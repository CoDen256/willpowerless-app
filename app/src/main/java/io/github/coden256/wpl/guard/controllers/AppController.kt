package io.github.coden256.wpl.guard.controllers

import android.content.Context
import android.util.Log
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.core.Owner
import io.github.coden256.wpl.guard.core.Owner.Companion.asOwner
import io.github.coden256.wpl.guard.util.getInstalledPackages
import io.github.coden256.wpl.judge.Action
import io.github.coden256.wpl.judge.JudgeRuling
import io.github.coden256.wpl.judge.findMatch

class AppController(
    private val context: Context,
    private val appConfig: AppConfig
) {
    fun onNewRulings(new: List<JudgeRuling>){
        appConfig.appRulings = new
        process(new)
    }

    fun onNewApp(pkg: String){
        val rulings = appConfig.appRulings
        Log.i("GuardAppController", "Processing $pkg packages against: $rulings")
        asOwner(context){
            processPackage(pkg, rulings)
        }
    }

    private fun process(rulings: List<JudgeRuling>){
        val packages = context.getInstalledPackages()
        Log.i("GuardAppController", "Processing ${packages.size} packages against: $rulings")
        asOwner(context){
            packages.forEach {
                processPackage(it.packageName, rulings)
            }
        }
    }

    private fun Owner.processPackage(pkg: String, rulings: List<JudgeRuling>) {
        val (match, reasons) = rulings.findMatch(pkg)
        val (action, path) = match
        val reason = "because matches: $path, out of $reasons"
        when (action) {
            Action.FORCE -> {
                Log.i("GuardAppController", "FORCING: $pkg, $reason")
                blockUninstall(pkg)
            }

            Action.BLOCK -> {
                Log.i("GuardAppController", "BLOCKING: $pkg, $reason")
                hide(pkg)
            }

            Action.ALLOW -> {}
        }
    }
}