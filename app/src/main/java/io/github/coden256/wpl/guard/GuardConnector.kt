package io.github.coden256.wpl.guard

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class GuardConnector: ReadOnlyProperty<Any?, GuardClient?> {

    private var value: GuardClient? = null

    fun connect(context: Context){
        context.bindGuard(guardConnection(
            onConnected = {value = it},
            onDisconnected = {value = null}
        ))
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): GuardClient? {
        return value
    }

    private fun Context.bindGuard(connection: ServiceConnection){
        val intent = Intent("io.github.coden256.wpl.guard.RULING_SETTING")
        intent.setPackage("com.celzero.bravedns")
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private fun guardConnection(onConnected: (GuardClient) -> Unit, onDisconnected: () -> Unit): ServiceConnection{
        return object: ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                service ?: return
                Log.i("Guard", "Connected to Guard Service")
                onConnected(GuardClient.Stub.asInterface(service))
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.w("Guard", "Disconnected from Guard Service")
                onDisconnected()
            }

        }
    }
}
