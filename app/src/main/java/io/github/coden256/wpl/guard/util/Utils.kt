package io.github.coden256.wpl.guard.util

import androidx.work.Data
import androidx.work.ListenableWorker.Result

fun kotlin.Result<*>.asWorkResult(
    data: Data.Builder? = null,
    retryIf: (Throwable) -> Boolean = { false }
): Result {
    return when {
        this.isSuccess -> data?.let { Result.success(it.build())}
            ?: Result.success()

        this.isFailure && retryIf(this.exceptionOrNull()!!) -> Result.retry()
        else -> data?.let {
            Result.failure(
                it.putString("error", exceptionOrNull()!!.message).build()
            )
        } ?: Result.failure()
    }
}