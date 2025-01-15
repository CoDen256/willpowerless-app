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


        val owner = Owner(this)
        if (owner.isAdmin.not()){
            Toast.makeText(this, "Not an admin, finishing...", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val service = BudgetService(this, owner, pack)

        if (service.isFirstStart()) {
            try {
            } catch (e: Exception) {
            }

            service.setFirstStart(false)
        }
        service.cancelResetAlarm()
        service.setWeeklyVpnResetAlarm(LocalTime.of(6, 0))
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
