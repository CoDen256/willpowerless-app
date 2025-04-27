package io.github.coden256.wpl.guard.util

import androidx.work.Data
import androidx.work.ListenableWorker.Result

fun kotlin.Result<*>.asWorkResult(data: Data? = null, retryIf: (Throwable) -> Boolean = {false}): Result {
    return when {
        this.isSuccess -> data?.let{Result.success(it)} ?: Result.success()
        this.isFailure && retryIf(this.exceptionOrNull()!!) -> Result.retry()
        else -> data?.let{Result.failure(it)} ?: Result.failure()
    }
}