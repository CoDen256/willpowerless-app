package io.github.coden256.wpl.guard.services

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.Intent.ACTION_PACKAGE_ADDED
import android.content.Intent.ACTION_PACKAGE_REMOVED
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.config.PersistentState
import io.github.coden256.wpl.guard.core.enqueuePeriodic
import io.github.coden256.wpl.guard.core.newNotificationChannel
import io.github.coden256.wpl.guard.core.notify
import io.github.coden256.wpl.guard.core.registerReceiver
import io.github.coden256.wpl.guard.workers.GuardJudgeUpdater
import io.github.coden256.wpl.guard.workers.GuardServiceHealthChecker
import org.koin.android.ext.android.inject
import java.time.Duration


private const val NOTIFICATION_ID = 1
private const val TAG = "GuardService"

class GuardService : Service(), OnSharedPreferenceChangeListener {

    private val appConfig by inject<AppConfig>()
    private val persistentState by inject<PersistentState>()
    private val binder by inject<GuardBinder>()

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Guard Service started: $intent")


        newNotificationChannel<GuardService>()
        val notification = notify<GuardService>("Willpowerless Guard is on!", NOTIFICATION_ID)
        startForeground(NOTIFICATION_ID, notification)

        registerWorkers()
        registerReceivers()
        registerListeners()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "Guard Service received a new connection: $intent")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "Guard Service unbounded: $intent")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.i(TAG, "Guard Service rebinding: $intent")
        super.onRebind(intent)
    }

    private fun registerReceivers(){
        registerReceiver<PackageUpdateReceiver> {
            addAction(ACTION_PACKAGE_ADDED)
            addAction(ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }
    }

    private fun registerWorkers(){
        enqueuePeriodic<GuardServiceHealthChecker>(Duration.ofMinutes(15), Duration.ofMinutes(15))
        enqueuePeriodic<GuardJudgeUpdater>(Duration.ofMinutes(15), Duration.ZERO)
    }

    private fun registerListeners(){
        persistentState.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.i(TAG, "Pref change, key: $key: ${persistentState[key]}")
    }

    //        val pack = "com.celzero.bravedns"
//        val service = BudgetService(context, Owner(context), pack)
//        val a = service.getAlarm()
//        if (a != null){
//            if (Instant.ofEpochMilli(a).isAfter(Instant.now())){
//                service.disableVPN()
//            }
//        }else{
//            service.enableVPN()
//        }


//    try {
//        asOwner(context){
//            blockUninstall("org.telegram.messenger.beta", false)
//            blockUninstall("com.celzero.bravedns", false)
//
//            clearUserRestriction(UserManager.DISALLOW_APPS_CONTROL)
//            clearUserRestriction(UserManager.DISALLOW_CONFIG_VPN)
//            hide("org.telegram.messenger", false)
//            enableBackupService(true)
//        }
//    }catch (e: Exception){
//        Log.e("Guard", "App Launch during init of apps failed", e)
//    }
}


