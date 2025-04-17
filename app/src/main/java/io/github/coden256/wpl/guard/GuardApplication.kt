package io.github.coden256.wpl.guard

import android.app.Application
import android.content.Context
import android.os.UserManager
import android.util.Log
import io.github.coden256.wpl.guard.core.Owner.Companion.asOwner
import io.github.coden256.wpl.guard.core.registerReceiver
import io.github.coden256.wpl.guard.services.PackageUpdateReceiver

class GuardApplication: Application(){
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Log.i("GuardApplication", "App has launched!")

        registerServicesAndReceivers(base)
    }

    private fun registerServicesAndReceivers(context: Context) {
//        startService(Intent(context, GuardService::class.java))

        registerReceiver<PackageUpdateReceiver> {
            addAction("android.intent.action.PACKAGE_ADDED")
            addDataScheme("package")
        }
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
}