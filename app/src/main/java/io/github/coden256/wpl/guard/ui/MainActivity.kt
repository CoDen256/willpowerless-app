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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkerParameters
import io.github.coden256.wpl.guard.GuardClient
import io.github.coden256.wpl.guard.GuardClientConnector
import io.github.coden256.wpl.guard.Ruling
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.core.enqueueOnce
import io.github.coden256.wpl.guard.core.enqueuePeriodic
import io.github.coden256.wpl.guard.ui.theme.GuardTheme
import io.github.coden256.wpl.guard.workers.GuardJudgeUpdater
import io.github.coden256.wpl.guard.workers.GuardServiceHealthChecker
import io.github.coden256.wpl.judge.RulingTree
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

// (package: mine | package:com.celzero.bravedns | package:org.telegram.messenger.willpowerless ) tag:Guard
class MainActivity : ComponentActivity() {
    private val persistentState by inject<AppConfig>()
    private val appConfig by inject<AppConfig>()

    private val braveConnector = GuardClientConnector("com.celzero.bravedns")
    private val telConnector = GuardClientConnector("org.telegram.messenger.willpowerless")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        braveConnector.connect(this)
        telConnector.connect(this)
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
                UpdatableTextComponent(this, appConfig, persistentState,telConnector, this)
            }
        }
    }

    private fun io(f: suspend () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) { f() }
    }

}

@Composable
fun UpdatableTextComponent(context: Context, appConfig: AppConfig, persistentState: AppConfig,
                           connect: () -> GuardClient?, owner: LifecycleOwner) {
    // State to hold our text value
    var dns by remember { mutableStateOf("dns Text") }
    var domains by remember { mutableStateOf("domains Text") }
    var chats by remember { mutableStateOf("chats Text") }
    var users by remember { mutableStateOf("users Text") }
    var vpn by remember { mutableStateOf("vpn Text") }
    var apps by remember { mutableStateOf("apps Text") }

    persistentState.rulingsLive.observe(owner){
//        dns = it.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the current text
        Text(
            text = dns,
            fontSize = 10.sp,
            modifier = Modifier.padding(16.dp)
        )

//        Text(
//            text = domains,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(16.dp)
//        )
//
//        Text(
//            text = chats,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(16.dp)
//        )
//
//        Text(
//            text = users,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(16.dp)
//        )
//
//        Text(
//            text = vpn,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(16.dp)
//        )
//
//        Text(
//            text = apps,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(16.dp)
//        )

        // Button to update the text
        Button(
            onClick = {
                persistentState.firstTimeLaunch = !persistentState.firstTimeLaunch
                appConfig.rulings = RulingTree.EMPTY
                context.enqueueOnce<GuardJudgeUpdater>()
//                dns = appConfig.dnsRulings.toString()
//                domains = appConfig.domainRulings.toString()
//                chats = appConfig.telegramChatRulings.toString()
////                users = appConfig.telegramUserRulings.toString()
////                vpn = appConfig.vpnRulings.toString()
//                connect()?.onRulings(listOf(Ruling().apply {
//                    this.path = "/domains/*.youtube.com"
//                    this.action = "BLOCK"
//                }, Ruling().apply {
//                    this.path = "/domains/*.reddits.com"
//                    this.action = "FORCE"
//                },Ruling().apply {
//                    this.path = "/domains/*.reddit.com/community"
//                    this.action = "BLOCK"},
//
//                    ruling("/dns/max:", "FORCE"),
////                    ruling("/dns/1-bdaacaaaeaaia", "FORCE"),
//                    ruling("/chats/*-p*-***", "BLOCK"),
//                    ruling("/users/*-*", "FORCE")
//                )
//                )
//                apps = appConfig.appRulings.toString()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Update Text")
        }
    }
}

fun ruling(path: String, action: String): Ruling {
    return  Ruling().apply {
        this.path = path
        this.action = action
    }
}