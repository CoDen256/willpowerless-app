package io.github.coden256.wpl.guard.ui.rule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.judge.JudgeRuling
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
    val rules: StateFlow<List<RuleEntry>>
}

class AppRulesViewModel : ViewModel(), KoinComponent, RulesViewModel {
    override val rules = MutableStateFlow<List<RuleEntry>>(emptyList())
    val appConfig by inject<AppConfig>()

    init {
        viewModelScope.launch {
            appConfig.appRulingsLive.asFlow()
                .map{it.map{ruling -> ruling.copy(path = ruling.path)}}
                .collect {
                val total = it.map { it.toEntry() }
                rules.value = total.sortedWith(
                    compareByDescending<RuleEntry> { it.action == RuleAction.FORCE }.thenByDescending { it.timestamp }
                )

            }
        }
    }
}
class TelegramRulesViewModel : ViewModel(), KoinComponent, RulesViewModel {
    override val rules = MutableStateFlow<List<RuleEntry>>(emptyList())
    val appConfig by inject<AppConfig>()

    init {
        viewModelScope.launch {
            appConfig.sentRulingsLive.map { it["org.telegram.messenger.willpowerless"] }.asFlow()
                .collect {
                    if (it == null) return@collect
                    val total = it.map { it.toEntry() }
                    rules.value = total.sortedWith(
                        compareByDescending<RuleEntry> { it.action == RuleAction.FORCE }.thenByDescending { it.timestamp }
                    )

                }
        }
    }
}

class BraveRulesViewModel : ViewModel(), KoinComponent, RulesViewModel {
    override val rules = MutableStateFlow<List<RuleEntry>>(emptyList())
    val appConfig by inject<AppConfig>()

    init {
        viewModelScope.launch {
            appConfig.sentRulingsLive.map { it["com.celzero.bravedns"] }.asFlow()
                .collect {
                    if (it == null) return@collect
                    val total = it.map { it.toEntry() }
                    rules.value = total.sortedWith(
                        compareByDescending<RuleEntry> { it.action == RuleAction.FORCE }.thenByDescending { it.timestamp }
                    )

                }
        }
    }
}