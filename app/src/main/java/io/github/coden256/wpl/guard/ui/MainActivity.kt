package io.github.coden256.wpl.guard.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.config.PersistentState
import io.github.coden256.wpl.guard.ui.theme.GuardTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val persistentState by inject<PersistentState>()
    private val appConfig by inject<AppConfig>()

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
                UpdatableTextComponent(this, appConfig)
            }
        }
    }

    private fun io(f: suspend () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) { f() }
    }

}

@Composable
fun UpdatableTextComponent( context: Context, appConfig: AppConfig) {
    // State to hold our text value
    var dns by remember { mutableStateOf("dns Text") }
    var domains by remember { mutableStateOf("domains Text") }
    var chats by remember { mutableStateOf("chats Text") }
    var users by remember { mutableStateOf("users Text") }
    var vpn by remember { mutableStateOf("vpn Text") }
    var apps by remember { mutableStateOf("apps Text") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the current text
        Text(
            text = dns,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = domains,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = chats,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = users,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = vpn,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = apps,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        // Button to update the text
        Button(
            onClick = {
                dns = appConfig.dnsRulings.toString()
                domains = appConfig.domainRulings.toString()
                chats = appConfig.telegramChatRulings.toString()
                users = appConfig.telegramUserRulings.toString()
                vpn = appConfig.vpnRulings.toString()
//                apps = appConfig.appRulings.toString()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Update Text")
        }
    }
}