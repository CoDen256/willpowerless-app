package io.github.coden256.wpl.judge

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter


data class RulingTree(
    private val root: JsonElement,
    private val gson: Gson
) {
    companion object {
        val EMPTY = RulingTree(JsonNull.INSTANCE, Gson())
    }

    override fun toString(): String {
        return gson.toJson(root)
    }

    fun getRuling(path: String): Result<RulingNode> {
        val rulingPath = if (!path.endsWith("/ruling")) {
            path.removeSuffix("/").plus("/ruling")
        } else path

        val node = root.at(rulingPath.removeSuffix("/"))
        return tryParse(node, path)
    }

    private fun JsonElement.at(path: String): JsonElement?{
        var current = this
        for (part in path.split('/').filter { it.isNotEmpty() }) {
            if (!current.isJsonObject) return null
            current = current.asJsonObject.get(part) ?: return null
        }
        return current
    }

    private fun tryParse(node: JsonElement?, path: String): Result<RulingNode> {
        return try {
            Result.success(gson.fromJson(node, RulingNode::class.java))
        } catch (e: Exception) {
            Result.failure(IllegalArgumentException("Unable to get ruling under $path", e))
        }
    }

    fun getRulings(path: String): Result<List<JudgeRuling>> {
        val baseNode = root.at(path.removeSuffix("/"))
        if (baseNode == null || !baseNode.isJsonObject) {
            return Result.failure(IllegalArgumentException("Rulings container($path) should be object, but was: $baseNode"))
        }

        val rulings = mutableListOf<JudgeRuling>()
        findRulingsRecursively(baseNode.asJsonObject, path.removeSuffix("/"), rulings)

        return Result.success(rulings)
    }

    private fun findRulingsRecursively(
        node: JsonObject,
        currentPath: String,
        results: MutableList<JudgeRuling>
    ) {
        // Check if current node has a "ruling" field
        node.get("ruling")?.takeIf { it.isJsonObject }?.let { rulingElement ->
            tryParse(rulingElement, currentPath).getOrNull()?.let { rulingNode ->
                results.add(JudgeRuling(rulingNode.action, currentPath))
            }
        }

        // Recursively check all object children
        node.entrySet().forEach { (key, value) ->
            if (value.isJsonObject) {
                val newPath = if (currentPath.isEmpty()) key else "$currentPath/$key"
                findRulingsRecursively(value.asJsonObject, newPath, results)
            }
        }
    }


    class Adapter: TypeAdapter<RulingTree>() {
        private val gson = Gson()
        override fun write(out: JsonWriter, value: RulingTree) {
            gson.toJson(value.root, out)
        }

        override fun read(`in`: JsonReader): RulingTree {
            val jsonElement = JsonParser.parseReader(`in`)
            return RulingTree(jsonElement, gson)
        }
    }

}

data class RulingNode(val action: Action, val reason: String? = null) {}
data class JudgeRuling(val action: Action, val path: String){
    override fun toString(): String {
        return "$path -> $action"
    }
}
enum class Action {
    BLOCK, ALLOW, FORCE
}