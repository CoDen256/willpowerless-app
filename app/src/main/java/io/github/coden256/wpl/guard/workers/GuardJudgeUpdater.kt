package io.github.coden256.wpl.guard.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.util.asWorkResult
import io.github.coden256.wpl.judge.Judge
import io.github.coden256.wpl.judge.RulingTree
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.net.UnknownHostException

class GuardJudgeUpdater(context: Context, params: WorkerParameters) :
    Worker(context, params), KoinComponent {

    private val appConfig by inject<AppConfig>()
    private val judge by inject<Judge>()

    override fun doWork(): Result {
        Log.i("GuardJudgeUpdater", "Running as service worker...")
        return run().asWorkResult()
    }

    private fun run(): kotlin.Result<RulingTree> {
        return judge
            .getRulingTree("/dev/mi")
            .onSuccess { tree ->
                Log.i("GuardJudgeUpdater", "Judge returned: $tree")
                tree.root.asJsonObject.addProperty("timestamp", System.nanoTime())
                appConfig.rulings = tree
            }.onFailure {
                Log.w("GuardJudgeUpdater", "Judge fucked up: $it")
            }
    }
}
