package io.github.coden256.wpl.guard

import android.app.Application
import android.content.Context
import android.util.Log
import io.github.coden256.wpl.guard.modules.RootModule
import io.github.coden256.wpl.guard.workers.GuardServiceHealthChecker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GuardApplication: Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.i("GuardApplication", "App has launched!!")
        if (base == null){
            Log.i("GuardApplication", "Didn't get any context :(")
            return
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("GuardApplication", "App has been created!")

        startKoin {
            androidContext(this@GuardApplication)
            koin.loadModules(RootModule.modules)
        }

        GuardServiceHealthChecker.run(this)
    }


    override fun onTerminate() {
        super.onTerminate()
        Log.i("GuardApplication", "App has terminated")
    }
}