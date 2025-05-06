package io.github.coden256.wpl.guard.workers

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.WorkInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.monitors.WorkResult
import io.github.coden256.wpl.guard.util.asWorkResult
import io.github.coden256.wpl.judge.Judge
import io.github.coden256.wpl.judge.RulingTree
import okio.IOException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.UnknownHostException

class GuardJudgeUpdater(context: Context, private val params: WorkerParameters) :
    Worker(context, params), KoinComponent {

    private val appConfig by inject<AppConfig>()
    private val judge by inject<Judge>()

    override fun doWork(): Result {
        Log.i("GuardJudgeUpdater", "Running as service worker (${params.id})...")
        val timestamp = System.currentTimeMillis()
        val outputData = Data.Builder().putLong("timestamp", timestamp)
        val result = run()
        result.onSuccess {
            appConfig.jobs += WorkResult(params.id.toString(), WorkInfo.State.SUCCEEDED, 100, timestamp, Long.MAX_VALUE, "GuardJudgeUpdater", null, null)
        }.onFailure {
            appConfig.jobs += WorkResult(params.id.toString(), WorkInfo.State.FAILED, 50, timestamp, Long.MAX_VALUE, "GuardJudgeUpdater", it.message, null)
        }
        return result.asWorkResult(outputData){
            it is java.io.IOException || it is IllegalStateException }
    }

    private fun run(): kotlin.Result<RulingTree> {
        return judge
            .getRulingTree("/dev/mi")
            .mapCatching { tree ->
                Log.i("GuardJudgeUpdater", "Judge returned: $tree")
                tree.root.asJsonObject.addProperty("timestamp", tree.timestamp)
                appConfig.rulings = tree
                if (tree.root.isJsonObject && tree.root.asJsonObject.has("status")
                    && tree.root.asJsonObject.getAsJsonPrimitive("status").asString == "error"){
                    throw IllegalStateException("Response has errors")
                }
                tree
            }
            .onFailure {
                Log.w("GuardJudgeUpdater", "Judge fucked up: $it")
            }
    }
}
