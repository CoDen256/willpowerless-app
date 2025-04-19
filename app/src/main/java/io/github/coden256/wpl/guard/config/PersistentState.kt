package io.github.coden256.wpl.guard.config

import android.content.Context
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.default.withDefault
import org.koin.core.component.KoinComponent

class PersistentState(context: Context) : SimpleKrate(context), KoinComponent {


    var firstTimeLaunch by booleanPref("is_first_time_launch")
        .withDefault<Boolean>(true)

    operator fun get(key: String?): Any? {
        return sharedPreferences.all[key]
    }
}