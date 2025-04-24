package io.github.coden256.wpl.guard.services

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PACKAGE_ADDED
import android.content.Intent.ACTION_PACKAGE_REMOVED
import android.util.Log
import androidx.annotation.RequiresPermission
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.core.enqueueOnce
import io.github.coden256.wpl.guard.core.enqueuePeriodic
import io.github.coden256.wpl.guard.core.network.NetworkConnectionMonitor
import io.github.coden256.wpl.guard.core.newNotificationChannel
import io.github.coden256.wpl.guard.core.notify
import io.github.coden256.wpl.guard.core.registerReceiver
import io.github.coden256.wpl.guard.controllers.AppController
import io.github.coden256.wpl.guard.controllers.RemoteAppRulingListener
import io.github.coden256.wpl.guard.controllers.VpnController
import io.github.coden256.wpl.guard.workers.GuardJudgeUpdater
import io.github.coden256.wpl.guard.workers.GuardServiceHealthChecker
import org.koin.android.ext.android.inject
import java.time.Duration


private const val NOTIFICATION_ID = 1
private const val TAG = "GuardService"

class GuardService : Service() {

    private val appConfig by inject<AppConfig>()
    private val appController by inject<AppController>()
    private val vpnController by inject<VpnController>()
    private val netMonitor by inject<NetworkConnectionMonitor>()

    private val remoteListeners = listOf(
        RemoteAppRulingListener("com.celzero.bravedns"),
        RemoteAppRulingListener("org.telegram.messenger.willpowerless")
    )


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Guard Service started: $intent")

        newNotificationChannel<GuardService>()
        val notification = notify<GuardService>("Willpowerless Guard is on!", NOTIFICATION_ID)
        startForeground(NOTIFICATION_ID, notification)

        connectListeners(this)

        registerWorkers()
        registerReceivers()
        registerListeners(this)
        return START_STICKY
    }

    override fun onDestroy() {
        disconnectListeners()
        super.onDestroy()
    }

    override fun onBind(intent: Intent) = null

    private fun registerWorkers(){
        enqueuePeriodic<GuardServiceHealthChecker>(
            duration = Duration.ofMinutes(15),
            init = Duration.ofMinutes(15),
            backoff = Duration.ofSeconds(10)
        )
        enqueuePeriodic<GuardJudgeUpdater>(
            duration = Duration.ofMinutes(15),
            init = Duration.ZERO
        )
    }

    private fun registerReceivers(){
        registerReceiver<PackageUpdateReceiver> {
            addAction(ACTION_PACKAGE_ADDED)
            addAction(ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }
    }

    private fun connectListeners(context: Context){
        remoteListeners.forEach { it.connect(context) }
        netMonitor.startMonitoring()
    }

    private fun registerListeners(context: Context){
        appConfig.rulingsLive.observeForever { tree ->
            Log.i("GuardService", "New rulings: $tree")

            appController.onRulings(tree.getRulings("/apps/").getOrNull() ?: emptyList())
            vpnController.onRulings(tree.getRulings("/vpn/").getOrNull() ?: emptyList())
            remoteListeners.forEach {
                it.onRulings(tree.getSubRulings("/apps/${it.target}/").getOrNull() ?: emptyList())
            }
        }

        netMonitor.onConnectionChanged = { enabled ->
            Log.w("GuardService", "Network enabled: $enabled")
            if (enabled) enqueueOnce<GuardJudgeUpdater>(backoff = Duration.ofSeconds(10))
        }
    }

    private fun disconnectListeners(){
        remoteListeners.forEach { it.disconnect(this) }
        netMonitor.stopMonitoring()
    }
}


