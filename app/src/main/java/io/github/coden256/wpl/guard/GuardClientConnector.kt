package io.github.coden256.wpl.guard

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log

class GuardClientConnector(val target: String): () -> GuardClient? {
    companion object {
        const val ACTION = "io.github.coden256.wpl.guard.RULING_SETTING"
    }

    var client: GuardClient? = null

    private val connection = guardConnection(
        onConnected = {client = it},
        onDisconnected = {client = null}
    )

    fun connect(context: Context){
        Log.i("GuardClientConnector", "Trying to connect to $target")
        val intent = Intent(ACTION).apply { setPackage(target) }
        context.bindService(intent, connection, BIND_AUTO_CREATE)
    }

    fun diconnect(context: Context){
        context.unbindService(connection)
    }

    override fun invoke(): GuardClient? {
        return client
    }

    private fun guardConnection(onConnected: (GuardClient) -> Unit, onDisconnected: () -> Unit): ServiceConnection{
        return object: ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                service ?: return
                Log.i("GuardConnector", "Connected to Guard Client: $name")
                onConnected(GuardClient.Stub.asInterface(service))
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.w("GuardConnector", "Disconnected from Guard Client: $name")
                onDisconnected()
            }
        }
    }

}
