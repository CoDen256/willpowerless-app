package io.github.coden256.wpl.guard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.lifecycle.lifecycleScope
import io.github.coden256.wpl.guard.ui.theme.GuardTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        val pack = "com.celzero.bravedns"
//
//        io {
//
//            val judge = OkHttpJudge(OkHttpClient())
//
//            val tree = judge.getRulingTree("/dev/mi").getOrThrow()
//            val ruling = tree.getRuling("/vpn/com.celzero.bravedns")
//            val ruling2 = tree.getRuling("/vpn/com.celzero.bravedns/ruling/")
//            val ruling3 = tree.getRuling("/apps/com.celzero.bravedns/ruling")
//            val ruling4 = tree.getRuling("/apps/com.celzero.bravedns/")
//
//            ruling4;ruling3;ruling2;ruling
//            val rulings = tree.getRulings("/apps")
//            val rulings2 = tree.getRulings("/vpn")
//            val rulings3 = tree.getRulings("/apps/com.celzero.bravedns/")
//
//            rulings2;rulings;rulings3
//
//        }
//
//
//        val owner = Owner(this)
//        if (owner.isAdmin.not()){
//            Toast.makeText(this, "Not an admin, finishing...", Toast.LENGTH_LONG).show()
//            finish()
//            return
//        }
//
//        val service = BudgetService(this, owner, pack)
//
//        if (service.isFirstStart()) {
//            try {
//            } catch (e: Exception) {
//            }
//
//            service.setFirstStart(false)
//        }
//        service.cancelResetAlarm()
//        service.setWeeklyVpnResetAlarm(LocalTime.of(6, 0))
//        val a = service.getAlarm()
//        if (a != null) {
//            if (Instant.ofEpochMilli(a).isAfter(Instant.now())) {
//                service.disableVPN()
//            }
//        } else {
//            service.enableVPN()
//        }

        setContent {
            GuardTheme {
                Column {
                    Text(
                        text = "Hi!",
                    )
                }
            }
        }
    }

    private fun io(f: suspend () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) { f() }
    }

}
