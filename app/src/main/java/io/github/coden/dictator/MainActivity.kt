package io.github.coden.dictator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.coden.dictator.budget.BudgetService
import io.github.coden.dictator.ui.BudgetAlarmApp
import io.github.coden.dictator.ui.BudgetApp
import io.github.coden.dictator.ui.theme.DictatorTheme
import java.time.Instant
import java.time.LocalTime


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val pack = "com.celzero.bravedns"

        val service = BudgetService(this, pack)
        if (service.isFirstStart()) {
            try {
                service.cancelResetAlarm()
            } catch (e: Exception) {
            }

            service.setWeeklyVpnResetAlarm(LocalTime.of(23, 25))
        }
        val a = service.getAlarm()
        if (a != null) {
            if (Instant.ofEpochMilli(a).isAfter(Instant.now())) {
                service.disableVPN()
            }
        } else {
            service.enableVPN()
        }

        setContent {
            DictatorTheme {
                BudgetAlarmApp(service)
            }
        }
    }

}
