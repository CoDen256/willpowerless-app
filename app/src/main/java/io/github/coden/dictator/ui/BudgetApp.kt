package io.github.coden.guard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
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
import io.github.coden.guard.budget.BudgetService
import io.github.coden.guard.budget.SessionTimer
import kotlin.time.Duration.Companion.seconds

@Composable
fun BudgetApp(service: BudgetService) {
    val remainingBudget = remember { mutableStateOf(service.getRemainingBudget()) }
    val vpnStatus = remember { mutableStateOf(service.getRemainingBudget() > 0) }
    var selectedHours by remember { mutableStateOf(0) }
    var selectedMinutes by remember { mutableStateOf(0) }


    val currentSessionTime = remember { mutableStateOf(0) }
    val sessionTimer = remember { mutableStateOf<SessionTimer?>(null) }

    var sessionDuration by remember { mutableStateOf(0) } // Store the requested session duration
    var isSessionActive by remember { mutableStateOf(false) }

    LaunchedEffect(sessionDuration) {
        if (sessionDuration > 0) {
            // Start session timer when session is requested
            sessionTimer.value = SessionTimer(sessionDuration, { remainingTime ->
                currentSessionTime.value = remainingTime
                service.reduceBudget(1)
                remainingBudget.value = service.getRemainingBudget()
            }) {
                // Re-enable VPN after session ends
                vpnStatus.value = true
//                service.enableVPN()
                remainingBudget.value = service.getRemainingBudget()
                sessionTimer.value = null
                isSessionActive = false
                sessionDuration = 0
            }
            sessionTimer.value?.start()
            // Set alarm to re-enable VPN after session duration
//            service.enableVpnAt(sessionDuration)
            isSessionActive = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        remainingBudget.value.seconds.toComponents { hours: Long, minutes: Int, seconds: Int, nanoseconds: Int ->
            Text(text = "Remaining Budget: ${hours}h ${minutes}m ${seconds}s")
        }
        Text(text = "VPN Status: ${if (vpnStatus.value) "Enabled" else "Disabled"}")

        Button(
            colors = ButtonDefaults.buttonColors(if (vpnStatus.value) Color.Red else Color.Green),
            onClick = {
                if (remainingBudget.value > 0 && vpnStatus.value) {
                    vpnStatus.value = false
                    service.disableVPN()
                } else if (!vpnStatus.value) {
                    vpnStatus.value = true
                    service.enableVPN()
                }
            }
        ) {
            Text(if (!vpnStatus.value) "Enable" else "Disable")
        }

        if (isSessionActive) {
            Text(text = "Session Time Left: ${currentSessionTime.value} seconds")
        }

        DurationSliderPicker({ h, m ->
            selectedHours = h
            selectedMinutes = m
        })

        Button(
            onClick = {
                val requestedTime = (selectedHours * 60 + selectedMinutes) * 60 // Example input
                if (remainingBudget.value >= requestedTime) {
                    remainingBudget.value = service.getRemainingBudget()
                    // Start the session and timer
                    sessionTimer.value?.cancel()
                    sessionDuration = requestedTime // Start timer with duration
                    service.disableVPN()
                    vpnStatus?.value = false
                }
            }
        ) {
            Text("Request Session")
        }
    }
}

@Composable
fun DurationSliderPicker(
    onDurationChange: (Int, Int) -> Unit
) {
    var selectedHours by remember { mutableStateOf(0f) }
    var selectedMinutes by remember { mutableStateOf(0f) }

    // Convert the hours and minutes into readable format
    val formattedDuration = formatDuration(selectedHours.toInt(), selectedMinutes.toInt())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Duration",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Duration: $formattedDuration",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Slider for hours
        Text("Hours")
        Slider(
            value = selectedHours,
            onValueChange = { newHours ->
                selectedHours = newHours
                onDurationChange(selectedHours.toInt(), selectedMinutes.toInt())
            },
            valueRange = 0f..24f,
            steps = 23,
            modifier = Modifier.fillMaxWidth()
        )

        // Slider for minutes
        Text("Minutes")
        Slider(
            value = selectedMinutes,
            onValueChange = { newMinutes ->
                selectedMinutes = newMinutes
                onDurationChange(selectedHours.toInt(), selectedMinutes.toInt())
            },
            valueRange = 0f..59f,
            steps = 60,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

fun formatDuration(hour: Int, minute: Int): String {
    return when {
        hour > 0 && minute > 0 -> "$hour hour ${minute} minutes"
        hour > 0 -> "$hour hour"
        minute > 0 -> "$minute minutes"
        else -> "0 minutes"
    }
}
