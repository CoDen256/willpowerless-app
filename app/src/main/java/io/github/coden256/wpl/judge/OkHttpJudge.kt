package io.github.coden256.wpl.judge

import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request


class OkHttpJudge(private val client: OkHttpClient): Judge {

    private val JUDGE_ENDPOINT = "http://217.154.222.188/rulings"

    override fun getRulingTree(path: String): Result<RulingTree> {
        if (!path.startsWith("/")) throw IllegalStateException("Path should start with /, was: $path")
        val request: Request = Request.Builder()
            .url(JUDGE_ENDPOINT+path)
            .build()
        val gson = Gson()
        try {
            client.newCall(request).execute().use { response ->
                try {
                    return Result.success(
                        RulingTree(JsonParser.parseReader(response.body?.charStream()), gson, System.currentTimeMillis())
                    )
                } catch (e: Exception) {
                    return Result.failure(e)
                }
            }
        }catch (e: Exception){
            return Result.failure(e)
        }

    }
}