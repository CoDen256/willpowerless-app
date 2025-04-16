package io.github.coden.judge

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper


data class RulingTree(
    private val root: JsonNode,
    private val mapper: ObjectMapper
) {
    fun getRuling(path: String): Result<Ruling>{
        val rulingPath = if (!path.endsWith("/ruling")){
            path.removeSuffix("/").plus("/ruling")
        } else path

        val node = root.at(rulingPath.removeSuffix("/"))
        return tryParse(node, path)
    }

    private fun tryParse(node: JsonNode?, path: String): Result<Ruling> {
        return try {
            Result.success(mapper.treeToValue(node, Ruling::class.java))
        } catch (e: Exception) {
            Result.failure(IllegalArgumentException("Unable to get ruling under $path", e))
        }
    }

    fun getRulings(path: String): Result<Map<String, Ruling>>{
        val node = root.at(path.removeSuffix("/"))
        if (!node.isObject) return Result.failure(IllegalArgumentException("Rulings container($path) should be object, but was: $node"))

        return Result.success(node
                .fields()
                .asSequence()
                .mapNotNull { entry ->
                    tryParse(entry.value.get("ruling"), "").getOrNull()?.let { entry.key to it } }
                .associate { it.first to it.second }
        )
        }

}

data class Ruling(val action: Action, val reason: String? = null){}

enum class Action() {
    BLOCK, ALLOW, FORCE
}