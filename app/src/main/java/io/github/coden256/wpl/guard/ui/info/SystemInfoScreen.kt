package io.github.coden256.wpl.guard.ui.info
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SystemInfoScreen(
    viewModel: SystemInfoViewModel,
    modifier: Modifier = Modifier
) {
    val systemInfo by viewModel.systemInfo.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(systemInfo) { info ->
            SystemInfoCard(info = info)
        }
    }
}

@Composable
fun SystemInfoCard(info: SystemInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when{
                info.value == "hidden" -> MaterialTheme.colorScheme.errorContainer
                info.value == "uninstallable" -> Color(0xABC3B74A)
                info.title == "Always on VPN" -> Color(0x408BC34A)
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when(info.title) {
                    "Android Version" -> Icons.Default.Android
                    "Device Model" -> Icons.Default.PhoneAndroid
                    "Screen Resolution" -> Icons.Default.DesktopWindows
                    "Battery Status" -> Icons.Default.BatteryStd
                    "Storage Space" -> Icons.Default.Storage
                    "Memory Info" -> Icons.Default.Memory
                    "Always on VPN" -> Icons.Default.Shield
                    else -> Icons.Default.Info
                },
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = info.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = info.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}