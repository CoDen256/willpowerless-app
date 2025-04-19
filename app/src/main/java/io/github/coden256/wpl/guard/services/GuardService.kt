package io.github.coden256.wpl.guard.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.Intent.ACTION_PACKAGE_ADDED
import android.content.Intent.ACTION_PACKAGE_REMOVED
import android.graphics.Color
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.coden256.wpl.guard.DNSRuling
import io.github.coden256.wpl.guard.DomainRuling
import io.github.coden256.wpl.guard.Guard
import io.github.coden256.wpl.guard.R
import io.github.coden256.wpl.guard.TelegramChatRuling
import io.github.coden256.wpl.guard.TelegramUserRuling
import io.github.coden256.wpl.guard.core.enqueuePeriodic
import io.github.coden256.wpl.guard.core.registerReceiver
import io.github.coden256.wpl.guard.ui.MainActivity
import io.github.coden256.wpl.guard.workers.GuardServiceHealthCheckWorker
import java.time.Duration


class GuardService : Service(){

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("GuardService", "Guard Service started: $intent")
        createNotificationChannel("channel-1", "Channel")
        registerWorkers()
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
        enqueuePeriodic<GuardServiceHealthCheckWorker>(Duration.ofMinutes(15), Duration.ofMinutes(15))
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun createNotificationChannel(channelId: String, channelName: String) {
        val resultIntent = Intent(
            this,
            MainActivity::class.java
        )
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntentWithParentStack(resultIntent)
        val resultPendingIntent: PendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)

        val chan =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = checkNotNull(getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentIntent(resultPendingIntent) //intent
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1, notificationBuilder.build())
        startForeground(1, notification)
    }
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
