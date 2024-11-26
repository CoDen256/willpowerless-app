package io.github.coden.dictator

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.UserManager
import android.util.Log
import io.github.coden.dictator.Owner.Companion.asOwner
import io.github.coden.dictator.service.DictatorService

class App: Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.i("Dictator", "App Launched")
        if (base == null){
            Log.e("Dictator", "Context not provided")
            return
        }

        initApps(base)
        registerPackageReceiver()
        registerService(base)
    }

    private fun registerService(context: Context) {
        startService(Intent(context, DictatorService::class.java))
    }

    private fun initApps(context: Context){
        try {
            asOwner(context){
                blockUninstall("org.telegram.messenger.beta", true)
                blockUninstall("com.celzero.bravedns", true)

                clearUserRestriction(UserManager.DISALLOW_APPS_CONTROL)
                clearUserRestriction(UserManager.DISALLOW_CONFIG_VPN)
//                hide("org.telegram.messenger", false)
                enableBackupService(true)
            }
        }catch (e: Exception){
            Log.e("Dictator", "App Launch during init of apps failed", e)
        }

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