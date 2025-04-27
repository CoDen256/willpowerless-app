package io.github.coden256.wpl.guard

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import io.github.coden256.wpl.guard.modules.RootModule
import io.github.coden256.wpl.guard.workers.GuardServiceHealthChecker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// (package: mine | package:com.celzero.bravedns | package:org.telegram.messenger.willpowerless ) tag:Guard
class GuardApplication : Application(), Configuration.Provider {

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    companion object {
        lateinit var workManager: WorkManager
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("GuardApplication", "App has been created!")

        startKoin {
            androidContext(this@GuardApplication)
            koin.loadModules(RootModule.modules)
        }

        GuardServiceHealthChecker.runNow(this)
        workManager = WorkManager.getInstance(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.i("GuardApplication", "App has terminated")
    }
}