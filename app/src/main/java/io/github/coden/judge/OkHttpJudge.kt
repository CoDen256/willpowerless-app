package io.github.coden.judge

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request


class OkHttpJudge(private val client: OkHttpClient): Judge {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
    private val JUDGE_ENDPOINT = "https://willpowerless-judge.up.railway.app/rulings"

    override fun getRulingTree(path: String): Result<RulingTree> {
        if (!path.startsWith("/")) throw IllegalStateException("Path should start with /, was: $path")
        val request: Request = Request.Builder()
            .url(JUDGE_ENDPOINT+path)
            .build()
        client.newCall(request).execute().use { response ->
            try {
                return Result.success(
                    RulingTree(objectMapper.readTree(response.body?.byteStream()), objectMapper)
                )
            } catch (e: Exception) {
                return Result.failure(e)
            }
        }
    }
}