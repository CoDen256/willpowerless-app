package io.github.coden256.wpl.guard

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log

private const val TAG = "GuardClientConnector"

class GuardClientConnector(val target: String): () -> GuardClient? {
    companion object {
        const val ACTION = "io.github.coden256.wpl.guard.RULING_SETTING"
    }

    var client: GuardClient? = null
    var connection: ServiceConnection? = null

    fun bind(context: Context,
             onConnected: (GuardClient) -> Unit = {client = it},
             onDisconnected: () -> Unit = {client = null}
    ){
        Log.i(TAG, "Trying to connect to $target")
        val intent = Intent(ACTION).apply { setPackage(target) }
        val con = guardConnection(onConnected, onDisconnected).also { connection = it }
        context.bindService(intent, con, BIND_AUTO_CREATE)
    }

    fun unbind(context: Context){
        Log.i(TAG, "Trying to disconnect from $target")
        connection?.let { context.unbindService(it) }
    }

    override fun invoke(): GuardClient? {
        return client
    }

    private fun guardConnection(onConnected: (GuardClient) -> Unit, onDisconnected: () -> Unit): ServiceConnection{
        return object: ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                service ?: return
                Log.i(TAG, "Connected to Guard Client: $name")
                onConnected(GuardClient.Stub.asInterface(service))
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.w(TAG, "Disconnected from Guard Client: $name")
                onDisconnected()
            }
        }
    }

}
