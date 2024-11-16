package io.github.coden.dictator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.coden.dictator.budget.BudgetService
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toKotlinDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetAlarmApp(service: BudgetService) {
    val dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss dd MMMM yyyy")
    val remainingBudget = remember { mutableStateOf(service.getRemainingBudget()) }
    val vpnStatus = remember { mutableStateOf(service.isVpnEnabled()) }
    var timeDialogVisible by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalDateTime.now().plusMinutes(15)) }
    var untilSessionEnd = remember { mutableStateOf(
        Duration.between(LocalDateTime.now(), selectedTime).toKotlinDuration()
    ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        remainingBudget.value.seconds.toComponents { hours: Long, minutes: Int, seconds: Int, nanoseconds: Int ->
            Text(text = "Remaining Budget: ${hours}h ${minutes}m ${seconds}s")
        }

        LaunchedEffect(Unit) {
            while (true) {
                delay(2000) // Delay for 2 seconds before checking the VPN status again
                vpnStatus.value = service.isVpnEnabled() // Update the state with current VPN status
            }
        }
        Text(text = "VPN Status: ${if (vpnStatus.value) "Enabled" else "Disabled"}",
            color = if (!vpnStatus.value) Color.Red else Color.Green)

        Button(
            colors = ButtonDefaults.buttonColors(if (vpnStatus.value) Color.Red else Color.Green),
            onClick = {
                if (vpnStatus.value) {
                    service.disableVPN()
                    vpnStatus.value = false
                } else {
                    service.enableVPN()
                    vpnStatus.value = true
                }
            }
        ) {
            Text(if (!vpnStatus.value) "Enable" else "Disable")
        }

        // Trigger to show the time picker dialog
        Button(onClick = { timeDialogVisible = true }) {
            Text("Pick Time")
        }

        // Show the TimeDial composable when dialogVisible is true
        if (timeDialogVisible) {
            AlarmPicker(
                onConfirm = { state ->
                    val now = LocalDateTime.now()
                    selectedTime = now.withHour(state.hour).withMinute(state.minute)
                    if (selectedTime.isBefore(now)){
                        selectedTime = selectedTime.plusDays(1)
                    }
                    untilSessionEnd.value =  Duration.between(LocalDateTime.now(), selectedTime).toKotlinDuration()
                    // Handle time confirmation
                    timeDialogVisible = false
                    // You can get the selected time from the timePickerState here
                },
                onDismiss = {
                    // Handle time picker dismissal
                    timeDialogVisible = false
                })
        }

        Text("Request a session until:")
        Text("${selectedTime.format(dateFormat)}")

        untilSessionEnd.value.toComponents { hours: Long, minutes: Int, seconds: Int, nanoseconds: Int ->
            Text(text = "Duration: ${hours}h ${minutes}m ${seconds}s")
        }
        Button(
            onClick = {
                 // Example input
            }
        ) {
            Text(text = "Request")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
// [START android_compose_components_advanced]
@Composable
fun AlarmPicker(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    AlarmPickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) },
    ) {

        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
fun AlarmPickerDialog(
    title: String = "Select Time",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier =
            Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    TextButton(onClick = onConfirm) { Text("OK") }
                }
            }
        }
    }
}
