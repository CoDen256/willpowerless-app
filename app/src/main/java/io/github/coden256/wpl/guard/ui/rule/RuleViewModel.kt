package io.github.coden256.wpl.guard.ui.rule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.judge.JudgeRuling
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.viewmodel.defaultExtras
import java.util.UUID


// Rule.kt
data class RuleEntry(
    val id: String,
    val path: String,
    val action: RuleAction,
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

enum class RuleAction {
    BLOCK, ALLOW, FORCE
}

fun JudgeRuling.toEntry(): RuleEntry {
    return RuleEntry(
        UUID.randomUUID().toString(),
        path,
        action = RuleAction.valueOf(action.toString()),
        description =  reason ?: "no reason specified",
        timestamp = timestamp
    )
}

// RulesViewModel.kt

interface RulesViewModel{
    val rules: MutableStateFlow<List<RuleEntry>>
    val isLoading: MutableStateFlow<Boolean>
    val scope: CoroutineScope
    fun getRulings(): LiveData<List<JudgeRuling>?>

    fun refresh() {
        scope.launch {
            try {
                isLoading.value = true
                getRulings().value?.let { update(it) }
            }finally {
                isLoading.value = false
            }
        }
    }

    fun start(){
        scope.launch {
            getRulings().asFlow().collect{
                if (it == null) return@collect
                update(it)
            }
        }
    }

    suspend fun update(list: List<JudgeRuling>){
        val total = list.map { it.toEntry() }
        rules.value = total.sortedWith(
            compareByDescending<RuleEntry> { it.action == RuleAction.FORCE }.thenByDescending { it.timestamp }
        )
    }
}

class AppRulesViewModel : ViewModel(), KoinComponent, RulesViewModel {
    override val rules = MutableStateFlow<List<RuleEntry>>(emptyList())
    override val isLoading = MutableStateFlow(false)
    override val scope: CoroutineScope = viewModelScope
    val appConfig by inject<AppConfig>()

    init {
        start()
    }

    override fun getRulings(): LiveData<List<JudgeRuling>?> {
        return appConfig.appRulingsLive.map { it }
    }

}
class TelegramRulesViewModel : ViewModel(), KoinComponent, RulesViewModel {
    override val rules = MutableStateFlow<List<RuleEntry>>(emptyList())
    override val isLoading = MutableStateFlow(false)
    override val scope: CoroutineScope = viewModelScope
    val appConfig by inject<AppConfig>()

    init {
        start()
    }

    override fun getRulings(): LiveData<List<JudgeRuling>?> {
       return appConfig.sentRulingsLive.map { it["org.telegram.messenger.willpowerless"] }
    }

}

class BraveRulesViewModel : ViewModel(), KoinComponent, RulesViewModel {
    override val rules = MutableStateFlow<List<RuleEntry>>(emptyList())
    override val isLoading = MutableStateFlow(false)
    override val scope: CoroutineScope = viewModelScope
    val appConfig by inject<AppConfig>()

    init {
        start()
    }

    override fun getRulings(): LiveData<List<JudgeRuling>?> {
        return appConfig.sentRulingsLive.map { it["com.celzero.bravedns"] }
    }
}