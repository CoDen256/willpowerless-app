package io.github.coden256.wpl.guard.monitors

import android.util.Log
import androidx.work.WorkInfo
import androidx.work.WorkManager
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.core.WORK_TAG

class WorkMonitor(private val workManager: WorkManager, private val appConfig: AppConfig) {
    fun register(){
        workManager
            .getWorkInfosByTagLiveData(WORK_TAG)
            .observeForever {
                Log.w("GuardWorkMonitor", "infos: $it")
                val current = it.map { it.toWorkResult() }.groupBy { it.id }
                val saved = appConfig.jobs.groupBy { it.id }

                val final = (current+saved)
                    .map { it.key }
                    .distinct()
                    .flatMap {resoveNew(saved[it], current[it])}

                appConfig.jobs = final.filterNot { it.isExpired() }.toSet()
            }
    }

    fun resoveNew(prev: List<WorkResult>?, new: List<WorkResult>?): List<WorkResult>{
        if (prev == null) return new ?: emptyList()
        if (new == null) return prev

        val finishedPrev = prev.filter { it.state.isFinished }
        val finishedNew = prev.filter { it.state.isFinished }
    }

    fun WorkResult.isExpired(): Boolean{
        return timestamp < java.time.Instant.now().minus(1, java.time.temporal.ChronoUnit.DAYS).nano
                && state.isFinished
    }

    fun WorkInfo.toWorkResult(): WorkResult {
        return WorkResult(
            id = id.toString(),
            state = state,
            value = when (state) {
                WorkInfo.State.SUCCEEDED -> 100
                WorkInfo.State.FAILED -> 100
                WorkInfo.State.RUNNING -> 50
                WorkInfo.State.ENQUEUED -> 25
                WorkInfo.State.BLOCKED -> 10
                WorkInfo.State.CANCELLED -> 0
            },
            scheduled = nextScheduleTimeMillis,
            timestamp = outputData.getLong("timestamp", 0L),
            type = tags.first { it.startsWith("name=") }.removePrefix("name="),
            error = outputData.getString("error"),
            data = outputData.getString("data")
        )
    }

}

data class WorkResult(
    val id: String,
    val state: WorkInfo.State,
    val value: Int = 100,
    val timestamp: Long,
    val scheduled: Long,
    val type: String,
    val error: String?,
    val data: String?,
)