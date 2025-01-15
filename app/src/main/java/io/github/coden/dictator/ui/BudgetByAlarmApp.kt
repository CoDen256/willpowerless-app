package io.github.coden.dictator.ui

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.coden.dictator.budget.BudgetService
import io.github.coden.dictator.budget.BudgetService.Companion.WEEKLY_BUDGET_SECONDS
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
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
    var untilSelected = remember {
        mutableStateOf(
            Duration.between(LocalDateTime.now(), selectedTime).toKotlinDuration()
        )
    }

    var currentAlarm by remember {
        mutableStateOf(service.getAlarm()
            ?.let { Instant.ofEpochMilli(it).atZone(ZoneId.of("CET")) })
    }

    var untilAlarm by remember {
        mutableStateOf(currentAlarm?.let {
            Duration.between(LocalDateTime.now(), it).toKotlinDuration()
        }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp, horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PrettyProgressBar({ remainingBudget.value / WEEKLY_BUDGET_SECONDS.toFloat(); })

        remainingBudget.value.seconds.toComponents { hours: Long, minutes: Int, seconds: Int, nanoseconds: Int ->
            Text(text = "Remaining Budget: ${hours}h ${minutes}m ${seconds}s")
        }

        LaunchedEffect(Unit) {
            while (true) {
                delay(1000) // Delay for 2 seconds before checking the VPN status again
                vpnStatus.value = service.isVpnEnabled() // Update the state with current VPN status

                currentAlarm = service.getAlarm()?.let {
                    val a = Instant.ofEpochMilli(it).atZone(ZoneId.of("CET"))
                    untilAlarm = Duration.between(LocalDateTime.now(), a).toKotlinDuration()
                    a
                }

                val now = LocalDateTime.now()
                if (remainingBudget.value < untilSelected.value.inWholeSeconds){
                    Toast.makeText(service.context, "Exceeds budget!", Toast.LENGTH_LONG).show()
                    selectedTime = LocalDateTime.now().plusSeconds(remainingBudget.value)
                }
                if (untilSelected.value.isNegative()){
                    selectedTime = LocalDateTime.now().plusMinutes(15)
                }
                untilSelected.value = Duration.between(now, selectedTime).toKotlinDuration()

            }
        }
        Text(
            text = buildAnnotatedString {
                append("Restriction Status (VPN): ")

                // Highlighted portion
                withStyle(SpanStyle(color = if (!vpnStatus.value) Color	(217,58,128) else Color	(54,184,145))) {
                    append(if (vpnStatus.value) "Enabled" else "Disabled")
                }
            }
        )

//        Button(
//            colors = ButtonDefaults.buttonColors(if (vpnStatus.value) Color.Red else Color.Green),
//            onClick = {
//                if (vpnStatus.value) {
//                    service.disableVPN()
//                    vpnStatus.value = false
//                } else {
//                    service.enableVPN()
//                    vpnStatus.value = true
//                }
//            }
//        ) {
//            Text(if (!vpnStatus.value) "Enable" else "Disable")
//        }

        currentAlarm?.let {
            Text("Current Session ends at: ${it.format(dateFormat)}")
            untilAlarm?.toComponents { hours: Long, minutes: Int, seconds: Int, nanoseconds: Int ->
                Text(text = "Time left: ${hours}h ${minutes}m ${seconds}s")
            }

            Button(
                onClick = {
                    service.enableVPN()
                    val left = Duration.between(LocalDateTime.now(), it)
                    service.reduceBudget(-left.seconds)
                    remainingBudget.value = service.getRemainingBudget()
                    vpnStatus.value = true
                    service.cancelAlarm()
                },
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Terminate this session")
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    service.reduceBudget(untilSelected.value.inWholeSeconds)
                    remainingBudget.value = service.getRemainingBudget()
                    if (remainingBudget.value <= 0){
                        Toast.makeText(service.context, "No budget left!", Toast.LENGTH_LONG).show()
                    }else{
                        service.enableVpnAt(selectedTime)
                        service.disableVPN()
                        vpnStatus.value = false
                    }
                }, enabled = currentAlarm == null
            ) {
                Text(text = "Request")
            }

            // Trigger to show the time picker dialog
            Button(onClick = { timeDialogVisible = true }, enabled = currentAlarm == null) {
                Text("Pick Time")
            }

            // Show the TimeDial composable when dialogVisible is true
            if (timeDialogVisible) {
                AlarmPicker(
                    onConfirm = { state ->
                        val now = LocalDateTime.now()
                        selectedTime = now.withHour(state.hour).withMinute(state.minute)
                        if (selectedTime.isBefore(now)) {
                            selectedTime = selectedTime.plusDays(1)
                        }
                        untilSelected.value =
                            Duration.between(now, selectedTime).toKotlinDuration()

                        if (remainingBudget.value < untilSelected.value.inWholeSeconds){
                            Toast.makeText(service.context, "Exceeds budget!", Toast.LENGTH_LONG).show()
                            selectedTime = LocalDateTime.now().plusSeconds(remainingBudget.value)
                        }
                        untilSelected.value = Duration.between(now, selectedTime).toKotlinDuration()
                        // Handle time confirmation
                        timeDialogVisible = false
                        // You can get the selected time from the timePickerState here
                    },
                    onDismiss = {
                        // Handle time picker dismissal
                        timeDialogVisible = false
                    })
            }


        }


        if (currentAlarm == null){
            Text("Request a new session until:")
            Text("${selectedTime.format(dateFormat)}", fontWeight = FontWeight.Bold)

            untilSelected.value.toComponents { hours: Long, minutes: Int, seconds: Int, nanoseconds: Int ->
                Text(text = buildAnnotatedString {
                    // Highlighted portion
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Duration: ${hours}h ${minutes}m ${seconds}s")
                    }
                })
            }
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
@Composable
fun PrettyProgressBar(percentage: () -> Float) {
    LinearProgressIndicator(
        progress = {
            percentage() // Convert percentage to 0.0 - 1.0
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp), // Adjust height for a prettier look
        color = Color(0xFFB2DFDB), // Pretty green color
        strokeCap = StrokeCap.Round
    )
}