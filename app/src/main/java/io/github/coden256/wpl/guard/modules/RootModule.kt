package io.github.coden256.wpl.guard.modules

import androidx.work.WorkManager
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.monitors.NetworkConnectionMonitor
import io.github.coden256.wpl.guard.controllers.AppController
import io.github.coden256.wpl.guard.controllers.VpnController
import io.github.coden256.wpl.guard.core.Owner
import io.github.coden256.wpl.guard.monitors.WorkMonitor
import io.github.coden256.wpl.judge.Judge
import io.github.coden256.wpl.judge.OkHttpJudge
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object RootModule {
    private val coreModule = module {
        single { OkHttpClient() }
        single<Judge> { OkHttpJudge(get()) }
        single { VpnController(get()) }
        single { AppController(get(), get()) }
        single { NetworkConnectionMonitor(androidContext()) }
        single { Owner(androidContext(), get()) }
        single<WorkManager> { WorkManager.getInstance(androidApplication()) }
        single { WorkMonitor(get(), get()) }
    }

    private val configModule = module {
        single { AppConfig(androidContext()) }
    }

    val modules = listOf(coreModule, configModule)
}