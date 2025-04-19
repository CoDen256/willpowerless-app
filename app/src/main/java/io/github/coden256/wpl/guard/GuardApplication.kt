package io.github.coden256.wpl.guard

import android.app.Application
import android.content.Context
import android.os.UserManager
import android.util.Log
import io.github.coden256.wpl.guard.core.Owner.Companion.asOwner
import io.github.coden256.wpl.guard.workers.GuardServiceHealthChecker

class GuardApplication: Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.i("GuardApplication", "App has launched!!")
        if (base == null){
            Log.i("GuardApplication", "Didn't get any context :(")
            return
        }

        GuardServiceHealthChecker.run(base)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("GuardApplication", "App has been created!")
    }


    override fun onTerminate() {
        super.onTerminate()
        Log.i("GuardApplication", "App has terminated")
    }

    private fun registerServicesAndReceivers() {


//

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