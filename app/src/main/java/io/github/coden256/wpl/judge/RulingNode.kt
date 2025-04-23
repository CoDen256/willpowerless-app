package io.github.coden256.wpl.judge

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonNull
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

    private fun JsonElement.at(path: String): JsonElement{
        var current = this
        for (part in path.split('/').filter { it.isNotEmpty() }) {
            current = current.asJsonObject.get(part)
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
        if (!node.isJsonObject) return Result.failure(IllegalArgumentException("Rulings container($path) should be object, but was: $node"))

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

    class JsonApadter: TypeAdapter<RulingTree>() {
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