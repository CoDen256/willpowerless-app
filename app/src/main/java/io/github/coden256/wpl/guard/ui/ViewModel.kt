import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import io.github.coden256.wpl.guard.core.WORK_TAG
import io.github.coden256.wpl.guard.core.enqueueOnce
import io.github.coden256.wpl.guard.workers.GuardJudgeUpdater
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WorkResult(
    val id: String,
    val state: WorkInfo.State,
    val value: Int = 100,
    val timestamp: Long,
    val scheduled: Long,
    val type: String
)

data class WorkResultsState(
    val results: List<WorkResult> = emptyList(),
    val isLoading: Boolean = false,
    val isWorkScheduled: Boolean = false
)

class WorkResultsViewModel(private val app: Application) : AndroidViewModel(app) {
    private val _state = MutableStateFlow(WorkResultsState())
    val state: StateFlow<WorkResultsState> = _state

    private val workManager = WorkManager.getInstance(app)
    init {
        viewModelScope.launch {
            // Observe work results
            workManager
                .getWorkInfosByTagLiveData(WORK_TAG)
                .asFlow()
                .collect { workInfos ->
                    val results = workInfos
                        .map { workInfo ->
                            WorkResult(
                                id = workInfo.id.toString(),
                                state = workInfo.state,
                                value = when(workInfo.state){
                                    WorkInfo.State.SUCCEEDED -> 100
                                    WorkInfo.State.FAILED -> 100
                                    WorkInfo.State.RUNNING -> 50
                                    WorkInfo.State.ENQUEUED -> 25
                                    WorkInfo.State.BLOCKED -> 10
                                    WorkInfo.State.CANCELLED -> 0
                                },
                                scheduled = workInfo.nextScheduleTimeMillis,
                                timestamp = workInfo.outputData.getLong("timestamp", 0L),
                                type = workInfo.tags.first { it.startsWith("name=") }.removePrefix("name=")
                            )
                        }

                    _state.update { it.copy(results = results.sortedByDescending { r -> r.timestamp }) }
                }
        }
    }

    fun schedulePeriodicWork() {
        app.enqueueOnce<GuardJudgeUpdater>()
    }
}