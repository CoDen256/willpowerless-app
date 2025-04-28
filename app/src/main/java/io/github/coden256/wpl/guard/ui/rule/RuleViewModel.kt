package io.github.coden256.wpl.guard.ui.rule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// Rule.kt
data class Rule(
    val id: String,
    val path: String,
    val action: RuleAction,
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

enum class RuleAction {
    BLOCK, ALLOW, FORCE
}

// RulesViewModel.kt

class RulesViewModel : ViewModel() {
    private val _rules = MutableStateFlow<List<Rule>>(emptyList())
    val rules: StateFlow<List<Rule>> = _rules

    init {
        loadSampleRules()
    }

    private fun loadSampleRules() {
        viewModelScope.launch {
            _rules.value = listOf(
                Rule(
                    id = "1",
                    path = "/api/user/*",
                    action = RuleAction.ALLOW,
                    description = "Allow all user API access"
                ),
                Rule(
                    id = "2",
                    path = "/api/admin/*",
                    action = RuleAction.BLOCK,
                    description = "Block admin API access"
                ),
                Rule(
                    id = "3",
                    path = "/system/update",
                    action = RuleAction.FORCE,
                    description = "Force system updates"
                ),
                Rule(
                    id = "4",
                    path = "/images/public/*",
                    action = RuleAction.ALLOW,
                    description = "Allow public image access"
                ),
                Rule(
                    id = "5",
                    path = "/config/*",
                    action = RuleAction.BLOCK,
                    description = "Block configuration access"
                )
            )
        }
    }

    fun addRule(rule: Rule) {
        _rules.value = _rules.value + rule
    }

    fun removeRule(id: String) {
        _rules.value = _rules.value.filter { it.id != id }
    }
}