package io.github.coden256.wpl.guard.config

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.default.withDefault
import hu.autsoft.krate.gson.gson
import hu.autsoft.krate.gson.gsonPref
import hu.autsoft.krate.stringPref
import hu.autsoft.krate.stringSetPref
import io.github.coden256.wpl.guard.monitors.WorkResult
import io.github.coden256.wpl.guard.util.liveData
import io.github.coden256.wpl.judge.JudgeRuling
import io.github.coden256.wpl.judge.RulingTree
import org.koin.core.component.KoinComponent

class AppConfig(val context: Context) : SimpleKrate(context), KoinComponent {

    init {
        gson = Gson().newBuilder()
            .registerTypeAdapter(RulingTree::class.java, RulingTree.Adapter())
            .create()
    }

    var jobs by gsonPref<Set<WorkResult>>().withDefault(setOf())
    val jobsLive = liveData(::jobs)

    val rulingsLive = MutableLiveData<RulingTree>() // liveData(::rulings)

    var appRulings by gsonPref<List<JudgeRuling>>().withDefault(listOf())
    var hiddenPackages by stringSetPref().withDefault(setOf())
    var uninstallablePackages by stringSetPref().withDefault(setOf())
    var vpnOnPackage by stringPref()

    fun addHiddenPackage(pkg: String){
        hiddenPackages = hiddenPackages.plus(pkg)
    }

    fun removeHiddenPackage(pkg: String){
        hiddenPackages = hiddenPackages.minus(pkg)
    }

    fun addUninstallablePackage(pkg: String){
        uninstallablePackages = uninstallablePackages.plus(pkg)
    }

    fun removeUninstallablePackage(pkg: String){
        uninstallablePackages = uninstallablePackages.minus(pkg)
    }

    operator fun get(key: String?): Any? {
        return sharedPreferences.all[key]
    }
}