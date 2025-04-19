package io.github.coden256.wpl.guard.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.coden256.wpl.guard.core.isServiceRunning
import io.github.coden256.wpl.guard.core.startForegroundService
import io.github.coden256.wpl.guard.services.GuardService

class GuardServiceHealthCheckWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result = run(context)

    companion object{

        fun run(context: Context): Result{
            Log.i("GuardServiceHealthCheckWorker", "Checking on guard")
            if (context.isServiceRunning<GuardService>()) {
                Log.i("GuardServiceHealthCheckWorker", "Guard is running, all good :)")
            } else{
                Log.i("GuardServiceHealthCheckWorker", "Guard is not running, restarting :(")
                context.startForegroundService<GuardService> { }
            }
            return Result.success()
        }

    }
}
