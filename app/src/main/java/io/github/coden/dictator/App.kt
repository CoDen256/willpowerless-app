package io.github.coden.dictator

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.util.Log


class App: Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.i("Dictator", "App Launched")
        registerPackageReceiver()

    }

    private fun registerPackageReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED")
        intentFilter.addDataScheme("package")
        registerReceiver(DictatorPackageUpdateReceiver(), intentFilter)
        Log.i("Dictator", "DictatorPackageReceiver registered")
    }
}