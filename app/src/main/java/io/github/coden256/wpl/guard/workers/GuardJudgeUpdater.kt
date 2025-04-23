package io.github.coden256.wpl.guard.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.judge.Judge
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GuardJudgeUpdater(context: Context, params: WorkerParameters) :
    Worker(context, params), KoinComponent {

    private val appConfig by inject<AppConfig>()
    private val judge by inject<Judge>()

    override fun doWork(): Result {
        Log.i("GuardJudgeUpdater", "Running as service worker...")
        try {
            run()
            return Result.success()
        } catch (e: Exception) {
            Log.e("GuardJudgeUpdater", "Unable to run judge updater: $e")
            return Result.failure()
        }
    }

    private fun run() {

        val tree = judge.getRulingTree("/dev/mi").getOrThrow()

        Log.i("GuardJudgeUpdater", "Got tree: $tree, updating app config")
        appConfig.rulings = tree.getRulings("/apps/com.celzero.bravedns/domains").getOrNull() ?: emptyList()

//        appConfig.appRulings = tree.getRulings("/apps").getOrNull() ?: emptyList()
//        appConfig.vpnRulings = tree.getRulings("/vpn").getOrNull() ?: emptyList()
//        appConfig.telegramUserRulings = tree.getRulings("/apps/$telegramBeta/user").getOrNull() ?: emptyList()
//        appConfig.telegramChatRulings = tree.getRulings("/apps/$telegramBeta/channels").getOrNull() ?: emptyList()
//        appConfig.domainRulings = tree.getRulings("/apps/$bravedns/domains").getOrNull() ?: emptyList()
//        appConfig.dnsRulings = tree.getRulings("/apps/$bravedns/dns").getOrNull() ?: emptyList()
    }
}
