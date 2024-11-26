package io.github.coden.dictator

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import io.github.coden.dictator.Owner.Companion.asOwner

class App: Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.i("Dictator", "App Launched")
        asOwner(base!!){
            hide("org.thunderdog.challegram",false)
        }
        registerPackageReceiver()
    }

    private fun registerPackageReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED")
        intentFilter.addDataScheme("package")
        val service = DictatorPackageUpdateReceiver()
        registerReceiver(service, intentFilter)
        Log.i("Dictator", "DictatorPackageReceiver registered")
    }
}