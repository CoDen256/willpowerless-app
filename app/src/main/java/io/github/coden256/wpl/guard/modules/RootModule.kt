package io.github.coden256.wpl.guard.modules

import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.config.PersistentState
import io.github.coden256.wpl.guard.services.GuardBinder
import io.github.coden256.wpl.judge.Judge
import io.github.coden256.wpl.judge.OkHttpJudge
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object RootModule {
    private val coreModule = module {
        single { OkHttpClient() }
        single<Judge> { OkHttpJudge(get()) }
        single { GuardBinder(get()) }
    }

    private val configModule = module {
        single { PersistentState(androidContext()) }
        single { AppConfig(androidContext(), get()) }
    }

    val modules = listOf(coreModule, configModule)
}