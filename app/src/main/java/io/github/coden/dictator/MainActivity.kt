package io.github.coden.dictator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.coden.dictator.budget.BudgetService
import io.github.coden.dictator.service.DictatorService
import io.github.coden.dictator.ui.BudgetAlarmApp
import io.github.coden.dictator.ui.theme.DictatorTheme
import java.time.Instant
import java.time.LocalTime


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val pack = "com.celzero.bravedns"

        startService(Intent(this, DictatorService::class.java))

        val service = BudgetService(this, pack)
        if (service.isAdmin.not()){
            Toast.makeText(this, "Not an admin, finishing...", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        if (service.isFirstStart()) {
            try {
                service.cancelResetAlarm()
            } catch (e: Exception) {
            }

            service.setWeeklyVpnResetAlarm(LocalTime.of(23, 39))
            service.setFirstStart(false)
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
