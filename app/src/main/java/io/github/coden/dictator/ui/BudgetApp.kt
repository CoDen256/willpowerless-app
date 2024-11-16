package io.github.coden.dictator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.coden.dictator.budget.BudgetService

@Composable
fun BudgetApp(service: BudgetService) {
    val remainingBudget = remember { mutableStateOf(service.getRemainingBudget()) }
    val vpnStatus = remember { mutableStateOf(service.getRemainingBudget() > 0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Remaining Budget: ${remainingBudget.value} minutes")
        Text(text = "VPN Status: ${if (vpnStatus.value) "Enabled" else "Disabled"}")

        Button(
            colors =  ButtonDefaults.buttonColors(if (!vpnStatus.value) Color.Red else Color.Green),
            onClick = {
                if (remainingBudget.value > 0 && vpnStatus.value) {
                    vpnStatus.value = false
                    service.disableVPN()
                }else if (!vpnStatus.value){
                    vpnStatus.value = true
                    service.enableVPN()
                }
            }
        ) {
            Text(if (!vpnStatus.value) "Enable" else "Disable")
        }

        OutlinedTextField(
            value = "",
            onValueChange = {
                /* Handle time input */
            },
            label = { Text("Request Time (minutes)") }
        )

        Button(
            onClick = {
                val requestedTime = 15 // Example input
                if (remainingBudget.value >= requestedTime) {
                    service.reduceBudget(requestedTime)
                    remainingBudget.value = service.getRemainingBudget()
                    service.disableVPN()
                    service.startTimer(requestedTime) {
                        vpnStatus.value = true
                        service.enableVPN()
                    }
                }
            }
        ) {
            Text("Request Session")
        }
    }
}