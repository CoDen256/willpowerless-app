package io.github.coden256.wpl.guard.core

import android.app.ActivityManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.time.Duration


inline fun <reified T: Service> Context.startForegroundService(build: Intent.() -> Unit = {}){
    // ContextCompat will take care of calling the proper service based on the API version.
    // before Android O, context.startService(intent) should be invoked.
    // on or after Android O, context.startForegroundService(intent) should be invoked.
    val intent = Intent(this, T::class.java)
    build(intent)
    ContextCompat.startForegroundService(this, intent )
    Log.i("GuardRegistry", "Registered for foreground: ${T::class.simpleName} ")
}

inline fun <reified T: BroadcastReceiver> Context.registerReceiver(build: IntentFilter.() -> Unit = {}){
    val intentFilter = IntentFilter()
    build(intentFilter)
    val receiver = T::class.java.getDeclaredConstructor().newInstance()
    registerReceiver(receiver, intentFilter)
    Log.i("GuardRegistry", "Registered: ${T::class.simpleName} ")
}

inline fun <reified T: Service> Context.isServiceRunning(): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager ?: return false
    return manager
        .getRunningServices(Integer.MAX_VALUE)
        .any { it.service.className == T::class.java.name }
}

inline fun <reified T: ListenableWorker> Context.enqueuePeriodic(
    duration: Duration=Duration.ofMinutes(15),
    init: Duration=Duration.ZERO,
    backoff: Duration=Duration.ofMillis(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS),
){
    val name = T::class.java.simpleName
    Log.i(name, "Enqueueing $name every $duration, starting in $init")
    val request = PeriodicWorkRequestBuilder<T>(duration)
        .setInitialDelay(init)
        .setBackoffCriteria(BackoffPolicy.LINEAR, backoff)
        .build()
    WorkManager
        .getInstance(this)
        .enqueueUniquePeriodicWork(
            name,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            request
    )
}

inline fun <reified T: ListenableWorker> Context.enqueueOnce(
    init: Duration=Duration.ZERO,
    backoff: Duration=Duration.ofMillis(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS)
){
    val name = T::class.java.simpleName + "-single"
    Log.i(name, "Enqueueing $name once, starting in $init")
    val request = OneTimeWorkRequestBuilder<T>()
        .setInitialDelay(init)
        .setBackoffCriteria(BackoffPolicy.LINEAR, backoff)
        .build()
    WorkManager
        .getInstance(this)
        .enqueueUniqueWork(name, ExistingWorkPolicy.REPLACE, request)
}