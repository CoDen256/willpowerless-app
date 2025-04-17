package io.github.coden256.wpl.guard.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.util.Log


inline fun <reified T: BroadcastReceiver> Context.registerReceiver(build: IntentFilter.() -> Unit = {}){
    val intentFilter = IntentFilter()
    build(intentFilter)
    val receiver = T::class.java.getDeclaredConstructor().newInstance()
    registerReceiver(receiver, intentFilter)
    Log.i("GuardRegistry", "Registered: ${T::class.simpleName} ")
}