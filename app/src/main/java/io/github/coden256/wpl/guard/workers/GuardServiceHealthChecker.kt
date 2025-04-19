package io.github.coden256.wpl.guard.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.coden256.wpl.guard.core.isServiceRunning
import io.github.coden256.wpl.guard.core.startForegroundService
import io.github.coden256.wpl.guard.services.GuardService

class GuardServiceHealthChecker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        Log.i("GuardServiceHealthChecker", "Running as service worker...")
        return runNow(context)
    }

    companion object{

        fun runNow(context: Context): Result{
            Log.i("GuardServiceHealthChecker", "Checking on guard")
            if (context.isServiceRunning<GuardService>()) {
                Log.i("GuardServiceHealthChecker", "Guard is running, all good :)")
            } else{
                Log.i("GuardServiceHealthChecker", "Guard is not running, restarting :(")
                context.startForegroundService<GuardService> { }
            }
            return Result.success()
        }
    }
}
