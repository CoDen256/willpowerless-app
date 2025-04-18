package io.github.coden256.wpl.guard.core

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContextCompat


inline fun <reified T: Service> Context.startForegroundService(){
    // ContextCompat will take care of calling the proper service based on the API version.
    // before Android O, context.startService(intent) should be invoked.
    // on or after Android O, context.startForegroundService(intent) should be invoked.
    ContextCompat.startForegroundService(this, Intent(this, T::class.java))
}

inline fun <reified T: BroadcastReceiver> Context.registerReceiver(build: IntentFilter.() -> Unit = {}){
    val intentFilter = IntentFilter()
    build(intentFilter)
    val receiver = T::class.java.getDeclaredConstructor().newInstance()
    registerReceiver(receiver, intentFilter)
    Log.i("GuardRegistry", "Registered: ${T::class.simpleName} ")
}