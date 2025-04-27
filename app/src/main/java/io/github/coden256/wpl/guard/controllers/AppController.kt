package io.github.coden256.wpl.guard.controllers

import android.content.pm.PackageInfo
import android.util.Log
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.core.Owner
import io.github.coden256.wpl.judge.Action
import io.github.coden256.wpl.judge.JudgeRuling
import io.github.coden256.wpl.judge.findMatch


private const val TAG = "GuardAppController"

class AppController(
    private val owner: Owner,
    private val appConfig: AppConfig
) {

    fun onNewRulings(rulings: List<JudgeRuling>) {
        appConfig.appRulings = rulings  // cache to check against later

        val packages = owner.getInstalledPackages()
        Log.i(TAG, "Processing ${packages.size} packages against: $rulings")

        val lockdown = rulings.firstOrNull { it.path.contains("*") && it.path.length < 5 }
        if (lockdown != null) {
            Log.e(TAG, "WARNING: RULINGS CONTAIN LOCKDOWN RULING: $lockdown")
            processLockdown(packages, rulings.filter { it.action == Action.FORCE })
            return
        }

        val prevUninstallable = appConfig.uninstallablePackages
        val prevHidden = appConfig.hiddenPackages
        Log.i(TAG, "hidden before:" + prevHidden)
        Log.i(TAG, "uninstallable before:" + prevUninstallable)

        appConfig.uninstallablePackages = setOf()
        appConfig.hiddenPackages = setOf()
        (packages.map { it.packageName }+prevHidden).forEach { // also process hidden, since they are...well, hidden
            processPackage(it, rulings)
        }

        prevHidden.minus(appConfig.hiddenPackages).forEach {
            Log.i(TAG, "Lifting up HIDDEN of $it")
            owner.hide(it, false)
        }

        prevUninstallable.minus(appConfig.uninstallablePackages).forEach {
            Log.i(TAG, "Lifting up UNINSTALLABLE of $it")
            owner.blockUninstall(it, false)
        }

        Log.i(TAG, "hidden:" + appConfig.hiddenPackages)
        Log.i(TAG, "uninstallable:" + appConfig.uninstallablePackages)
    }

    fun onNewApp(pkg: String) {
        val rulings = appConfig.appRulings
        Log.i(TAG, "Processing $pkg packages against: $rulings")
        processPackage(pkg, rulings)

        Log.i(TAG, "hidden:" + appConfig.hiddenPackages)
        Log.i(TAG, "uninstallable:" + appConfig.uninstallablePackages)
    }

    private fun processPackage(pkg: String, rulings: List<JudgeRuling>) {
        val (match, reasons) = rulings.findMatch(pkg)
        val (action, path) = match
        val reason = "because: $path, out of $reasons"
        when (action) {
            Action.FORCE -> {
                Log.i(TAG, "FORCING: $pkg, $reason")
                owner.blockUninstall(pkg)
            }

            Action.BLOCK -> {
                Log.i(TAG, "BLOCKING: $pkg, $reason")
                owner.hide(pkg)
            }

            Action.ALLOW -> {}
        }
    }

    private fun processLockdown(pkgs: List<PackageInfo>, allowed: List<JudgeRuling>) {
        Log.e(TAG, "ENABLING TOTAL LOCKDOWN, EXCEPT: $allowed")
        // TODO proper locktask or something else
        // - locktask (not working AND must have proper in between apps navigation, since only one locked app supported?) (implement in LocktastController /mi/lockdown/*apps -> FORCE)
        // - hide everything (but the homescreen arrangement is broken afterwards) (implement here)
        // - custom android launcher with selected packages (remoteapplistenre, force custom launcher, pass him rulings /com.launcher/lockdown/*.apps -> FORCE
    }
}