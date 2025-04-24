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
     val root: JsonElement,
     val gson: Gson
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
        val node = root.at(path.removeSuffix("/"))
        if (node == null || !node.isJsonObject) return Result.failure(IllegalArgumentException("Rulings container($path) should be object, but was: $node"))

        return Result.success(
            node
                .asJsonObject
                .entrySet()
                .asSequence()
                .mapNotNull { entry ->
                    tryParse(entry.value.asJsonObject.get("ruling"), "").getOrNull()?.let { entry.key to it }
                }
                .map { JudgeRuling(it.second.action, it.first) }
                .toList()
        )
    }

    fun getSubRulings(path: String): Result<List<JudgeRuling>> {
        val baseNode = root.at(path.removeSuffix("/"))
        if (baseNode == null || !baseNode.isJsonObject) {
            return Result.failure(IllegalArgumentException("Rulings container($path) should be object, but was: $baseNode"))
        }

        val rulings = mutableListOf<JudgeRuling>()
        findRulingsRecursively(baseNode.asJsonObject, "", rulings) // Start with empty relative path

        return Result.success(rulings)
    }

    private fun findRulingsRecursively(
        node: JsonObject,
        relativePath: String,  // Path relative to the starting node
        results: MutableList<JudgeRuling>
    ) {
        // Check all children first (skip the root ruling)
        node.entrySet().forEach { (key, value) ->
            if (value.isJsonObject) {
                val newRelativePath = if (relativePath.isEmpty()) key else "$relativePath/$key"

                // Check if this child has a "ruling" field
                value.asJsonObject.get("ruling")?.takeIf { it.isJsonObject }?.let { rulingElement ->
                    tryParse(rulingElement, newRelativePath).getOrNull()?.let { rulingNode ->
                        results.add(JudgeRuling(rulingNode.action, "/$newRelativePath"))
                    }
                }

                // Recurse deeper
                findRulingsRecursively(value.asJsonObject, newRelativePath, results)
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
    companion object {
        val DEFAULT = JudgeRuling(Action.ALLOW, "*")
    }
}
enum class Action {
    BLOCK, ALLOW, FORCE
}

data class Match(val value: JudgeRuling, val allMatching: List<JudgeRuling>)

fun JudgeRuling.matches(target: String): Boolean{
    return Regex(path
        .replace(".", "\\.")
        .replace("*", ".*"))
        .matches(target)
}

fun List<JudgeRuling>.getPrimaryRuling(): JudgeRuling{
    return firstOrNull { it.action == Action.FORCE } ?:
          firstOrNull {it.action == Action.BLOCK} ?:
          firstOrNull {it.action == Action.ALLOW} ?:
          JudgeRuling.DEFAULT
}

fun List<JudgeRuling>.findMatch(path: String): Match{
    val matching = filter { it.matches(path) }
    return Match(matching.getPrimaryRuling(), matching)
}