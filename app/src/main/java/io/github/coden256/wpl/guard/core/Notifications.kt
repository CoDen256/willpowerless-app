package io.github.coden256.wpl.guard.core

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.Service.NOTIFICATION_SERVICE
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.coden256.wpl.guard.R
import io.github.coden256.wpl.guard.ui.MainActivity

inline fun <reified T: Any> Context.newNotificationChannel(){
    val simpleName = T::class.simpleName ?: "NA CHANNEL"
    val chan = NotificationChannel(simpleName, simpleName, NotificationManager.IMPORTANCE_HIGH)
    chan.lightColor = Color.BLUE
    chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

    val manager = checkNotNull(getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
    manager.createNotificationChannel(chan)
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
inline fun <reified T: Any> Service.notify(title: String, notificationId: Int): Notification {
    val simpleName = T::class.simpleName ?: "NA CHANNEL"
    val resultIntent = Intent(this, MainActivity::class.java)
    // Create the TaskStackBuilder and add the intent, which inflates the back stack
    val stackBuilder = TaskStackBuilder.create(this).addNextIntentWithParentStack(resultIntent)

    val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)

    val notificationBuilder = NotificationCompat.Builder(this, simpleName)
    val notification: Notification = notificationBuilder.setOngoing(true)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(title)
        .setPriority(NotificationManager.IMPORTANCE_MIN)
        .setCategory(Notification.CATEGORY_SERVICE)
        .setContentIntent(resultPendingIntent) //intent
        .build()

    val notificationManager = NotificationManagerCompat.from(this)
    notificationManager.notify(notificationId, notificationBuilder.build())
    return notification
}