package io.github.coden256.wpl.guard.util

import androidx.work.ListenableWorker.Result

fun kotlin.Result<*>.asWorkResult(retryIf: (Throwable) -> Boolean = {false}): Result {
    return when {
        this.isSuccess -> Result.success()
        this.isFailure && retryIf(this.exceptionOrNull()!!) -> Result.retry()
        else -> Result.failure()
    }
}