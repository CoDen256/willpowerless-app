package io.github.coden256.wpl.judge

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import okhttp3.OkHttpClient
import okhttp3.Request


class OkHttpJudge(private val client: OkHttpClient): Judge {

    private val JUDGE_ENDPOINT = "https://willpowerless-judge.up.railway.app/rulings"

    override fun getRulingTree(path: String): Result<RulingTree> {
        if (!path.startsWith("/")) throw IllegalStateException("Path should start with /, was: $path")
        val request: Request = Request.Builder()
            .url(JUDGE_ENDPOINT+path)
            .build()
        val gson = Gson()
        client.newCall(request).execute().use { response ->
            try {
                return Result.success(
                    RulingTree(JsonParser.parseReader(response.body?.charStream()), gson)
                )
            } catch (e: Exception) {
                return Result.failure(e)
            }
        }
    }
}