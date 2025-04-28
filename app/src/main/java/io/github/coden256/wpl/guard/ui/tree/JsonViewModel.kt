package io.github.coden256.wpl.guard.ui.tree// JsonViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import io.github.coden256.wpl.guard.config.AppConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class JsonViewModel : ViewModel(), KoinComponent {
    val jsonString = MutableStateFlow("")
    val jsonTimestamp = MutableStateFlow(0L)
    val appConfig by inject<AppConfig>()

    init {
        viewModelScope.launch {
            appConfig.rulingsLive.asFlow().collect {
                jsonString.value = it.pretty()
                jsonTimestamp.value = it.timestamp
            }
        }
    }
}