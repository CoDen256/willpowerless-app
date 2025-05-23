package io.github.coden256.wpl.guard.ui.work

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.core.enqueueOnce
import io.github.coden256.wpl.guard.monitors.WorkResult
import io.github.coden256.wpl.guard.workers.GuardJudgeUpdater
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class WorkResultsState(
    val results: List<WorkResult> = emptyList(),
    val updated: Long = 0L
)

class WorkResultsViewModel(private val app: Application) : AndroidViewModel(app), KoinComponent {
    val state = MutableStateFlow(WorkResultsState())
    val isRefreshing = MutableStateFlow(false)
    private val appConfig by inject<AppConfig>()

    init {
        viewModelScope.launch {
            appConfig.jobsLive.asFlow().collect { results ->
                update(results)
            }
        }
    }

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch {
            try {
                val results = appConfig.jobsLive.value ?: return@launch

                update(results)

            } finally {
                isRefreshing.value = false
            }
        }
    }

    private fun update(results: Set<WorkResult>) {
        Log.w("GuardWorkersView", "jobs: $results")
        state.update {
            it.copy(
                results = results.sortedByDescending { r ->
                    if(r.timestamp!=0L) r.timestamp  else r.scheduled
                                                     },
                updated = System.nanoTime()
            )
        }
    }

    fun schedulePeriodicWork() {
        app.enqueueOnce<GuardJudgeUpdater>()
    }
}