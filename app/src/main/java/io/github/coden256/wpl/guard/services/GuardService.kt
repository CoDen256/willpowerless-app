package io.github.coden256.wpl.guard.services

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.Intent.ACTION_PACKAGE_ADDED
import android.content.Intent.ACTION_PACKAGE_REMOVED
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import io.github.coden256.wpl.guard.DNSRuling
import io.github.coden256.wpl.guard.DomainRuling
import io.github.coden256.wpl.guard.Guard
import io.github.coden256.wpl.guard.TelegramChatRuling
import io.github.coden256.wpl.guard.TelegramUserRuling
import io.github.coden256.wpl.guard.core.enqueuePeriodic
import io.github.coden256.wpl.guard.core.newNotificationChannel
import io.github.coden256.wpl.guard.core.notify
import io.github.coden256.wpl.guard.core.registerReceiver
import io.github.coden256.wpl.guard.workers.GuardServiceHealthChecker
import java.time.Duration


private const val NOTIFICATION_ID = 1

class GuardService : Service(){

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("GuardService", "Guard Service started: $intent")


        newNotificationChannel<GuardService>()
        val notification = notify<GuardService>("Willpowerless Guard is on!", NOTIFICATION_ID)
        startForeground(NOTIFICATION_ID, notification)

        registerWorkers()
        registerReceivers()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i("GuardService", "Guard Service received a new connection: $intent")
        return GuardBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("GuardService", "Guard Service unbounded: $intent")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.i("GuardService", "Guard Service rebinding: $intent")
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

object GuardBinder: Guard.Stub (){
    override fun dnsRulings(): List<DNSRuling> {
        return listOf(
            DNSRuling().also {
                it.dns = "1-bdaacaaaeaaia"
                it.action = "FORCE"
            }
        )
    }

    override fun domainRulings(): List<DomainRuling> {
        return listOf(
            DomainRuling().also {
                it.domain = "*.reddit.com"
                it.action = "BLOCK"
            }
        )
    }

    override fun telegramChatRulings(): List<TelegramChatRuling> {
        TODO("Not yet implemented")
    }

    override fun telegramUserRulings(): List<TelegramUserRuling> {
        TODO("Not yet implemented")
    }
}
