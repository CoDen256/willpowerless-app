package io.github.coden256.wpl.guard.config

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.default.withDefault
import hu.autsoft.krate.gson.gson
import hu.autsoft.krate.gson.gsonPref
import io.github.coden256.wpl.judge.JudgeRuling
import io.github.coden256.wpl.judge.RulingTree
import org.koin.core.component.KoinComponent

class AppConfig(val context: Context) : SimpleKrate(context), KoinComponent {

    init {
        gson = Gson().newBuilder()
            .registerTypeAdapter(RulingTree::class.java, RulingTree.Adapter())
            .create()
    }
    var firstTimeLaunch by booleanPref().withDefault(true)

//    var rulings by gsonPref<RulingTree>().withDefault(RulingTree.EMPTY) TODO do we need to persist the rulings?
    val rulingsLive = MutableLiveData<RulingTree>() // liveData(::rulings)

    var appRulings by gsonPref<List<JudgeRuling>>().withDefault(listOf())

    operator fun get(key: String?): Any? {
        return sharedPreferences.all[key]
    }
}