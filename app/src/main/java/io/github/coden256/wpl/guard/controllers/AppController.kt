package io.github.coden256.wpl.guard.controllers

import android.content.Context
import android.content.pm.PackageInfo
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

    fun onNewRulings(rulings: List<JudgeRuling>) {
        appConfig.appRulings = rulings

        val packages = context.getInstalledPackages()
        Log.i("GuardAppController", "Processing ${packages.size} packages against: $rulings")

        val lockdown = rulings.firstOrNull { it.path.contains("*") && it.path.length < 5 }
        if (lockdown != null) {
            Log.e("GuardAppController", "WARNING: RULINGS CONTAIN LOCKDOWN RULING: $lockdown")
            processLockdown(packages, rulings.filter { it.action == Action.FORCE })
            return
        }

        val prevHidden = appConfig.appsHidden
        val prevBlocked = appConfig.appsUninstallBlocked

        appConfig.appsHidden = setOf()
        appConfig.appsUninstallBlocked = setOf()
        asOwner(context) {
            packages.forEach {
                processPackage(it.packageName, rulings)
            }
            prevBlocked.minus(appConfig.appsUninstallBlocked).forEach {
                Log.i("GuardAppController", "Lifting up forcing unblock for $it")
                blockUninstall(it, false)
            }
            prevHidden.minus(appConfig.appsHidden).forEach {
                Log.i("GuardAppController", "Lifting up hiding for $it")
                hide(it, false)
            }
        }
        Log.i("GuardAppController", "hidden"+appConfig.appsHidden.toString())
        Log.i("GuardAppController", "blocked"+appConfig.appsUninstallBlocked.toString())
    }

    fun onNewApp(pkg: String) {
        val rulings = appConfig.appRulings
        Log.i("GuardAppController", "Processing $pkg packages against: $rulings")
        asOwner(context) {
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
                appConfig.addUninstallBlockedPackage(pkg)
            }

            Action.BLOCK -> {
                Log.i("GuardAppController", "BLOCKING: $pkg, $reason")
                hide(pkg)
                appConfig.addHiddenPackage(pkg)
            }

            Action.ALLOW -> {}
        }
    }

    private fun processLockdown(pkgs: List<PackageInfo>, allowed: List<JudgeRuling>) {
        Log.e("GuardAppController", "ENABLING TOTAL LOCKDOWN, EXCEPT: $allowed")
        // TODO proper locktask or something else
        // - locktask (not working AND must have proper in between apps navigation, since only one locked app supported?) (implement in LocktastController /mi/lockdown/*apps -> FORCE)
        // - hide everything (but the homescreen arrangement is broken afterwards) (implement here)
        // - custom android launcher with selected packages (remoteapplistenre, force custom launcher, pass him rulings /com.launcher/lockdown/*.apps -> FORCE
    }
}