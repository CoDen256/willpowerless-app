package io.github.coden256.wpl.guard.controllers

import android.app.ActivityManager
import android.app.ActivityOptions
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
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
    fun onNewRulings(rulings: List<JudgeRuling>){
        appConfig.appRulings = rulings

        val packages = context.getInstalledPackages().map { it.packageName }
        Log.i("GuardAppController", "Processing ${packages.size} packages against: $rulings")

        val lockdown = rulings.firstOrNull { it.path.contains("*") && it.path.length < 5 }
        if (lockdown != null){
            Log.e("GuardAppController", "WARNING: RULINGS CONTAIN LOCKDOWN RULING: $lockdown")
            processLockdown(packages, rulings.filter { it.action == Action.FORCE}.map { it.path })
            return
        }
        processLockdown(packages, listOf(
            "org.telegram.messenger.willpowerless",
            "com.celzero.bravedns",
            "io.github.coden256.wpl.guard "
        ))

        asOwner(context){
            packages.forEach {
                processPackage(it, rulings)
            }
        }
    }

    fun onNewApp(pkg: String){
        val rulings = appConfig.appRulings
        Log.i("GuardAppController", "Processing $pkg packages against: $rulings")
        asOwner(context){
            processPackage(pkg, rulings)
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

    private fun processLockdown(pkgs: List<String>, allowed: List<String>){
        Log.e("GuardAppController", "ENABLING TOTAL LOCKDOWN, EXCEPT: $allowed")
        asOwner(context){
            run {
//                this.setLockTaskPackages(adminComponent, allowed.toTypedArray())
//                val options = ActivityOptions.makeBasic()
//                options.setLockTaskEnabled(true)
//
//                this.setLockTaskFeatures(adminComponent,
//                    DevicePolicyManager.LOCK_TASK_FEATURE_HOME or
//                            DevicePolicyManager.LOCK_TASK_FEATURE_OVERVIEW)
//
//                val am = context.getSystemService<ActivityManager>() as ActivityManager?
//                am?.lockTaskModeState
            }
        }
    }
}