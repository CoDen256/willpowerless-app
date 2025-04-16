package io.github.coden.judge

interface Judge {

    fun getRulingTree(path: String): Result<RulingTree>
}

