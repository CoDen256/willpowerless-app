package io.github.coden256.wpl.guard.ui.rule

// RuleCard.kt
// RulesScreen.kt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

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
        RuleAction.BLOCK ->  MaterialTheme.colorScheme.onErrorContainer
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
        RuleAction.BLOCK ->  MaterialTheme.colorScheme.onError
        RuleAction.FORCE -> MaterialTheme.colorScheme.onTertiary
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesScreen(
    viewModel: RulesViewModel,
    modifier: Modifier = Modifier
) {
    val rules by viewModel.rules.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add rule") },
                text = { Text("Add Rule") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rules, key = { it.id }) { rule ->
                RuleCard(
                    rule = rule,
                    onDelete = { viewModel.removeRule(rule.id) }
                )
            }
        }

        if (showAddDialog) {
            AddRuleDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { newRule ->
                    viewModel.addRule(newRule)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
private fun AddRuleDialog(
    onDismiss: () -> Unit,
    onAdd: (Rule) -> Unit
) {
    var path by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var action by remember { mutableStateOf(RuleAction.ALLOW) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Rule") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = path,
                    onValueChange = { path = it },
                    label = { Text("Path Pattern") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    RuleAction.values().forEach { ruleAction ->
                        FilterChip(
                            selected = action == ruleAction,
                            onClick = { action = ruleAction },
                            label = { Text(ruleAction.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ruleAction.toContainerColor(),
                                selectedLabelColor = ruleAction.toSelectedContainerColor()
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (path.isNotBlank()) {
                        onAdd(
                            Rule(
                                id = UUID.randomUUID().toString(),
                                path = path,
                                action = action,
                                description = description
                            )
                        )
                    }
                },
                enabled = path.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun RuleCard(
    rule: Rule,
    modifier: Modifier = Modifier,
    onDelete: (() -> Unit)? = null
) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()) }
    val formattedDate = remember(rule.timestamp) {
        dateFormat.format(Date(rule.timestamp))
    }


    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = rule.action.toContainerColor(),
            contentColor = rule.action.toSelectedContainerColor()
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
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
                    overflow = TextOverflow.Ellipsis
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

                if (onDelete != null) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete rule",
                            tint = rule.action.toSelectedContainerColor()
                        )
                    }
                }
            }
        }
    }
}