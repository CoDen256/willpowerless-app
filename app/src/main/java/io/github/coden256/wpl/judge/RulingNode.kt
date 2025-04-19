package io.github.coden256.wpl.judge

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper


data class RulingTree(
    private val root: JsonNode,
    private val mapper: ObjectMapper
) {
    fun getRuling(path: String): Result<RulingNode> {
        val rulingPath = if (!path.endsWith("/ruling")) {
            path.removeSuffix("/").plus("/ruling")
        } else path

        val node = root.at(rulingPath.removeSuffix("/"))
        return tryParse(node, path)
    }

    private fun tryParse(node: JsonNode?, path: String): Result<RulingNode> {
        return try {
            Result.success(mapper.treeToValue(node, RulingNode::class.java))
        } catch (e: Exception) {
            Result.failure(IllegalArgumentException("Unable to get ruling under $path", e))
        }
    }

    fun getRulings(path: String): Result<List<Ruling>> {
        val node = root.at(path.removeSuffix("/"))
        if (!node.isObject) return Result.failure(IllegalArgumentException("Rulings container($path) should be object, but was: $node"))

        return Result.success(
            node
            .fields()
            .asSequence()
            .mapNotNull { entry ->
                tryParse(entry.value.get("ruling"), "").getOrNull()?.let { entry.key to it }
            }
            .map { Ruling(it.second.action, it.first) }
            .toList()
        )
    }

}

data class RulingNode(val action: Action, val reason: String? = null) {}
data class Ruling(val action: Action, val path: String){
    override fun toString(): String {
        return "$path -> $action"
    }
}
enum class Action() {
    BLOCK, ALLOW, FORCE
}