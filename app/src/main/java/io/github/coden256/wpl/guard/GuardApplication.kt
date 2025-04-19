package io.github.coden256.wpl.guard

import android.app.Application
import android.content.Context
import android.util.Log
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
}