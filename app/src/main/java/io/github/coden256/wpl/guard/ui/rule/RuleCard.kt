package io.github.coden256.wpl.guard.ui.rule

// RuleCard.kt
// RulesScreen.kt
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RuleAction.toContainerColor() =
    when (this) {
        RuleAction.ALLOW -> MaterialTheme.colorScheme.primaryContainer
        RuleAction.BLOCK -> MaterialTheme.colorScheme.errorContainer
        RuleAction.FORCE -> Color(0x3E8BC34A)
    }

@Composable
fun RuleAction.toSelectedContainerColor() =
    when (this) {
        RuleAction.ALLOW -> MaterialTheme.colorScheme.onPrimaryContainer
        RuleAction.BLOCK -> MaterialTheme.colorScheme.onErrorContainer
        RuleAction.FORCE -> MaterialTheme.colorScheme.onTertiaryContainer
    }

@Composable
fun RuleAction.toColor() =
    when (this) {
        RuleAction.ALLOW -> MaterialTheme.colorScheme.primary
        RuleAction.BLOCK -> MaterialTheme.colorScheme.error
        RuleAction.FORCE -> Color(0xFF8BC34A)
    }

@Composable
fun RuleAction.toSelectedColor() =
    when (this) {
        RuleAction.ALLOW -> MaterialTheme.colorScheme.onPrimary
        RuleAction.BLOCK -> MaterialTheme.colorScheme.onError
        RuleAction.FORCE -> MaterialTheme.colorScheme.onTertiary
    }


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RulesScreen(
    viewModel: RulesViewModel = viewModel(),
    modifier: Modifier = Modifier.padding(8.dp)

) {
    val rules by viewModel.rules.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Swipe-to-refresh state
    val refreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.refresh() }
    )

    Box(modifier = modifier.pullRefresh(refreshState)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rules) { rule ->
                RuleCard(
                    rule = rule,
                )
            }
        }

        // Pull-to-refresh indicator
        PullRefreshIndicator(
            refreshing = isLoading,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun RuleCard(
    rule: RuleEntry,
    modifier: Modifier = Modifier,
    onDelete: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val dateFormat = remember { SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()) }
    val formattedDate = remember(rule.timestamp) { dateFormat.format(Date(rule.timestamp)) }

    val (containerColor, contentColor) = rule.action.toContainerColor() to rule.action.toSelectedContainerColor()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),  // Smooth expansion animation
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        onClick = { expanded = !expanded }  // Toggle expansion on click
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Always visible content
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rule.path,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Badge(
                    containerColor = rule.action.toColor(),
                    contentColor = rule.action.toSelectedColor()
                ) {
                    Text(
                        text = rule.action.name,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            // Expandable content
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))

                    if (rule.description.isNotBlank()) {
                        Text(
                            text = rule.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}