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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.work.WorkInfo
import io.github.coden256.wpl.guard.monitors.WorkResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkResultsScreen(viewModel: WorkResultsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Periodic Work Results") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Control Panel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.schedulePeriodicWork() },
                    enabled = !state.isWorkScheduled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                    Spacer(Modifier.width(8.dp))
                    Text("Start")
                }
            }

            // Stats Chip Group
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = {},
                    label = { Text("Total: ${state.results.size}") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.List,
                            contentDescription = "Total",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )

                AssistChip(
                    onClick = {},
                    label = { Text("Success: ${state.results.count { it.state == WorkInfo.State.SUCCEEDED }}") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Success",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }

            // Results List
            if (state.results.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No results yet. Start periodic work or add a test result.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.results) { result ->
                        WorkResultCard(result = result)
                    }
                }
            }
        }
    }
}

@Composable
fun WorkResultCard(result: WorkResult) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, HH:mm:ss", Locale.getDefault()) }
    val formattedDate = remember(result.timestamp) {
        dateFormat.format(Date(result.timestamp))
    }

    val formattedScheduled = remember(result.timestamp) {
        dateFormat.format(Date(result.scheduled))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when(result.state){
                WorkInfo.State.SUCCEEDED -> MaterialTheme.colorScheme.surfaceVariant
                WorkInfo.State.FAILED -> MaterialTheme.colorScheme.errorContainer
                WorkInfo.State.RUNNING -> MaterialTheme.colorScheme.surfaceDim
                WorkInfo.State.ENQUEUED -> MaterialTheme.colorScheme.surfaceDim
                WorkInfo.State.BLOCKED -> MaterialTheme.colorScheme.surfaceDim
                WorkInfo.State.CANCELLED -> MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = result.type,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = when(result.state){
                        WorkInfo.State.SUCCEEDED -> MaterialTheme.colorScheme.primary
                        WorkInfo.State.FAILED -> MaterialTheme.colorScheme.error
                        WorkInfo.State.RUNNING -> MaterialTheme.colorScheme.secondary
                        WorkInfo.State.ENQUEUED -> MaterialTheme.colorScheme.secondary
                        WorkInfo.State.BLOCKED -> MaterialTheme.colorScheme.tertiary
                        WorkInfo.State.CANCELLED -> MaterialTheme.colorScheme.error
                    }
                )

                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = when(result.state){
                        WorkInfo.State.SUCCEEDED -> Icons.Default.CheckCircle
                        WorkInfo.State.FAILED -> Icons.Default.Error
                        WorkInfo.State.RUNNING -> Icons.Default.PlayArrow
                        WorkInfo.State.ENQUEUED -> Icons.Default.AccessTime
                        WorkInfo.State.BLOCKED -> Icons.Default.Cached
                        WorkInfo.State.CANCELLED -> Icons.Default.AlarmOff
                    },
                    contentDescription = "state",
                    tint = when(result.state){
                        WorkInfo.State.SUCCEEDED -> Color(0xFF4CAF50)
                        WorkInfo.State.FAILED -> MaterialTheme.colorScheme.error
                        WorkInfo.State.RUNNING -> Color(0xFF4CAF50)
                        WorkInfo.State.ENQUEUED -> Color(0xFF4CAF50)
                        WorkInfo.State.BLOCKED -> MaterialTheme.colorScheme.error
                        WorkInfo.State.CANCELLED -> MaterialTheme.colorScheme.error
                    }
                )

                Text(
                    text = when(result.state){
                        WorkInfo.State.SUCCEEDED -> "Completed successfully"
                        WorkInfo.State.FAILED -> "Completed with errors"
                        WorkInfo.State.RUNNING -> "Running"
                        WorkInfo.State.ENQUEUED -> "Enqueued"
                        WorkInfo.State.BLOCKED -> "Blocked"
                        WorkInfo.State.CANCELLED -> "Cancelled"
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = result.value / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when(result.state){
                    WorkInfo.State.SUCCEEDED -> MaterialTheme.colorScheme.primary
                    WorkInfo.State.FAILED -> MaterialTheme.colorScheme.error
                    WorkInfo.State.RUNNING -> MaterialTheme.colorScheme.secondary
                    WorkInfo.State.ENQUEUED -> MaterialTheme.colorScheme.secondary
                    WorkInfo.State.BLOCKED -> MaterialTheme.colorScheme.tertiary
                    WorkInfo.State.CANCELLED -> MaterialTheme.colorScheme.error
                },
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Scheduled: $formattedScheduled",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = result.id.take(8),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}