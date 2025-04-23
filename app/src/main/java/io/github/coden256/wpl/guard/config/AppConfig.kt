package io.github.coden256.wpl.guard.config

import android.content.Context
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.default.withDefault
import hu.autsoft.krate.gson.gsonPref
import io.github.coden256.wpl.guard.util.liveData
import io.github.coden256.wpl.judge.JudgeRuling
import org.koin.core.component.KoinComponent

class AppConfig(val context: Context) : SimpleKrate(context), KoinComponent {
    var firstTimeLaunch by booleanPref("is_first_time_launch").withDefault(true)

    var rulings by gsonPref<List<JudgeRuling>>("rulings").withDefault(emptyList())
    var rulingsLive = liveData(this::rulings){ rulings }

    operator fun get(key: String?): Any? {
        return sharedPreferences.all[key]
    }
}