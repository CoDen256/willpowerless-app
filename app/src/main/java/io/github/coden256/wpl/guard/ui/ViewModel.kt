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
import java.util.UUID

data class WorkResult(
    val id: String,
    val success: Boolean,
    val value: Int,
    val timestamp: Long,
    val type: String = if ((0..1).random() == 0) "Analysis" else "Processing"
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
//                        .filter { it.state == WorkInfo.State.SUCCEEDED }
                        .map { workInfo ->
                            WorkResult(
                                id = UUID.randomUUID().toString(),
                                success = false,
                                value = 0,
                                timestamp = 0L
                            )
                        }

                    _state.update { it.copy(results = results.sortedByDescending { r -> r.timestamp }) }
                }
        }
    }

    fun schedulePeriodicWork() {
        app.enqueueOnce<GuardJudgeUpdater>()

        _state.update { it.copy(isWorkScheduled = true) }
    }

    fun cancelPeriodicWork() {
        _state.update { it.copy(isWorkScheduled = false) }
    }

    fun addMockResult() {
        val newResult = WorkResult(
            id = UUID.randomUUID().toString(),
            success = (0..1).random() == 1,
            value = (1..100).random(),
            timestamp = System.currentTimeMillis()
        )

        _state.update { currentState ->
            currentState.copy(results = listOf(newResult) + currentState.results)
        }
    }
}