package io.github.coden256.wpl.guard.ui// ViewModelFactory.kt
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.coden256.wpl.guard.ui.info.SystemInfoViewModel
import io.github.coden256.wpl.guard.ui.rule.RulesViewModel
import io.github.coden256.wpl.guard.ui.work.WorkResultsViewModel

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WorkResultsViewModel::class.java) -> {
                WorkResultsViewModel(application) as T
            }
            modelClass.isAssignableFrom(SystemInfoViewModel::class.java) -> {
                SystemInfoViewModel(application) as T
            }
            modelClass.isAssignableFrom(RulesViewModel::class.java) -> {
                RulesViewModel() as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel() as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}