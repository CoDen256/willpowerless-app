package io.github.coden256.wpl.judge

interface Judge {

    fun getRulingTree(path: String): Result<RulingTree>
}

