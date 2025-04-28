package io.github.coden256.wpl.guard.workers

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.WorkInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.core.isServiceRunning
import io.github.coden256.wpl.guard.core.startForegroundService
import io.github.coden256.wpl.guard.monitors.WorkResult
import io.github.coden256.wpl.guard.services.GuardService
import io.github.coden256.wpl.guard.util.asWorkResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GuardServiceHealthChecker(private val context: Context, val params: WorkerParameters) :
    Worker(context, params), KoinComponent {

        val appConfig by inject<AppConfig> ()

    override fun doWork(): Result {
        Log.i("GuardServiceHealthChecker", "Running as service worker...")
        val timestamp = System.currentTimeMillis()
        val outputData = Data.Builder().putLong("timestamp", timestamp)
        val result = runNow(context)
        result.onSuccess {
            appConfig.jobs += WorkResult(params.id.toString(), WorkInfo.State.SUCCEEDED, 100, timestamp, Long.MAX_VALUE, "GuardServiceHealthChecker", null, null)
        }.onFailure {
            appConfig.jobs += WorkResult(params.id.toString(), WorkInfo.State.FAILED, 50, timestamp, Long.MAX_VALUE, "GuardServiceHealthChecker", it.message, null)
        }
        return result.asWorkResult(outputData){ it is IllegalStateException}
    }

    companion object{
        fun runNow(context: Context): kotlin.Result<Unit>{
            Log.i("GuardServiceHealthChecker", "Checking on guard")
            if (context.isServiceRunning<GuardService>()) {
                Log.i("GuardServiceHealthChecker", "Guard is running, all good :)")
                return kotlin.Result.success(Unit)
            } else{
                Log.i("GuardServiceHealthChecker", "Guard is not running, restarting :(")
                context.startForegroundService<GuardService> { }
                return kotlin.Result.failure(IllegalStateException("Guard not running"))
            }
        }
    }
}
