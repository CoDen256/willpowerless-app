package io.github.coden.guard

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.UserManager
import android.util.Log
import io.github.coden.guard.Owner.Companion.asOwner
import io.github.coden.guard.service.GuardService

class App: Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.i("Guard", "App Launched")
        if (base == null){
            Log.e("Guard", "Context not provided")
            return
        }

        initApps(base)
        registerPackageReceiver()
        registerServices(base)
    }

    private fun registerServices(context: Context) {
        startService(Intent(context, GuardService::class.java))
    }

    private fun initApps(context: Context){
        try {
            asOwner(context){
                blockUninstall("org.telegram.messenger.beta", false)
                blockUninstall("com.celzero.bravedns", false)

                clearUserRestriction(UserManager.DISALLOW_APPS_CONTROL)
                clearUserRestriction(UserManager.DISALLOW_CONFIG_VPN)
                hide("org.telegram.messenger", false)
                enableBackupService(true)
            }
        }catch (e: Exception){
            Log.e("Guard", "App Launch during init of apps failed", e)
        }

    }

    private fun registerPackageReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED")
        intentFilter.addDataScheme("package")
        val service = GuardPackageUpdateReceiver()
        registerReceiver(service, intentFilter)
        Log.i("Guard", "GuardPackageReceiver registered")
    }
}