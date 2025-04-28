package io.github.coden256.wpl.guard.core

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.UserManager
import android.util.Log
import android.widget.Toast
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.services.AdminReceiver
import java.util.concurrent.Executors

class Owner(
    private val context: Context,
    private val appConfig: AppConfig
) {
    private val executor = Executors.newSingleThreadExecutor()
    private val dpm = context.getSystemService(DevicePolicyManager::class.java)
    val admin = ComponentName(context, AdminReceiver::class.java)
    val isAdmin = dpm.isDeviceOwnerApp(context.packageName)

    private val tag = "GuardOwner"

    init {
        verify("initialize owner")
    }

    fun run(action: DevicePolicyManager.()->(Unit)): DevicePolicyManager?{
        dpm?.action()
        return dpm
    }

    //e.g. key=UserManager.DISALLOW_CONFIG_VPN
    fun clearUserRestriction(key: String){
        dpm.clearUserRestriction(admin, key)
    }

    fun hide(pkg: String, hide: Boolean=true){
        if (hide) appConfig.addHiddenPackage(pkg) else appConfig.removeHiddenPackage(pkg)

        if (dpm.isApplicationHidden(admin, pkg) == hide) return // no change
        if (hide && dpm.isUninstallBlocked(admin, pkg)) blockUninstall(pkg, false) // unblock before hiding

        Log.i(tag, "Hiding $pkg? $hide")
        dpm.setApplicationHidden(admin, pkg, hide)
    }

    fun blockUninstall(pkg: String, block: Boolean=true){
        if (block) appConfig.addUnremovablePackage(pkg) else appConfig.removeUnremovablePackage(pkg)
        if (dpm.isUninstallBlocked(admin, pkg) == block) return // no change
        if (block && dpm.isApplicationHidden(admin, pkg)) hide(pkg, false) // unhide before blocking

        Log.i(tag, "Blocking uninstall $pkg? $block")
        dpm.setUninstallBlocked(admin, pkg, block)
    }

    fun enableBackupService(enable:Boolean=true){
        Log.i(tag, "Enabling backup? $enable")
        dpm.setBackupServiceEnabled(admin, enable)
    }

    fun setAlwaysOnVpn(pkg: String){
        if (appConfig.vpnOnPackage != pkg){
            Log.i(tag, "Setting always on vpn: $pkg")
            dpm.addUserRestriction(admin, UserManager.DISALLOW_CONFIG_VPN)
            dpm.setAlwaysOnVpnPackage(admin, pkg, true)
            appConfig.vpnOnPackage = pkg
        }
    }

    fun removeAlwaysOnVpn(){
        if (appConfig.vpnOnPackage != null){
            Log.i(tag, "Removing always on vpn: ${appConfig.vpnOnPackage}")
            dpm.clearUserRestriction(admin, UserManager.DISALLOW_CONFIG_VPN)
            dpm.setAlwaysOnVpnPackage(admin, null, false)
            appConfig.vpnOnPackage = null
        }
    }

    fun transferOwnership(pkg: String, adminReceiver: String){
        Log.i(tag, "Transferring ownership to $pkg/$adminReceiver")
        val targetAdmin = ComponentName(pkg, "$pkg$adminReceiver")
        dpm.transferOwnership(admin, targetAdmin, null)
    }

    fun uninstall(packageName: String) {
        Log.i(tag, "Uninstalling $packageName...")

        // Check if the app is installed
        val packageManager = context.packageManager
        try {
            packageManager.getPackageInfo(packageName, 0)

            // If installed, clear app data (optional)
            dpm.clearApplicationUserData(admin, packageName, executor) { s, success ->
                if (success) {
                    // Proceed to uninstall the app
                    blockUninstall(packageName, false)
                    Log.i(tag,"App uninstallation allowed for $packageName")
                } else {
                    Log.i(tag,"Failed to clear data for $packageName")
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.i(tag,"App $packageName is not installed")
        }
    }

    private fun verify(op: String = "unspecified"){
        if (!isAdmin){
            Log.e(tag, "Not an admin, operation denied: <$op>")
            Toast.makeText(context, "${context.packageName} is not an admin, sorry :(", Toast.LENGTH_LONG).show()
        }
    }

    fun getInstalledPackages(): List<PackageInfo> {
        return context.packageManager
            .getInstalledPackages(0) // 0 for basic info
            .filterNot(systemFilter(context))
    }

    fun systemFilter(context: Context): (PackageInfo) -> Boolean = {
        it.packageName.startsWith("com.android")
                || it.packageName.startsWith("android")
                || (it.packageName.startsWith("com.google") && !it.packageName.contains("youtube"))
                || it.packageName.startsWith(context.packageName)
                || it.packageName.startsWith("com.xiaomi")
                || it.packageName.startsWith("com.mi")
                || it.packageName.startsWith("org.mi")
                || it.packageName.contains("miui")
                || it.packageName.contains("xiaomi")
                || it.packageName.startsWith("com.qualcomm")
                || it.packageName.startsWith("com.facebook")
                || it.packageName.startsWith("com.quicinc")
                || it.packageName.startsWith("org.chromium")
                || it.packageName.startsWith("com.wdstechnology")
                || it.packageName.startsWith("com.fingerprints")
                || it.packageName.startsWith("com.bsp")
                || it.packageName.startsWith("com.qti")
                || it.packageName.startsWith("com.lbe")
                || it.packageName.startsWith("com.logicapp")
                || it.packageName.startsWith("org.codeaurora")
                || it.packageName.startsWith("vendor")
                || it.packageName.startsWith("org.ifaa")
                || it.packageName.startsWith("de.telekom")
                || it.packageName.startsWith("com.fido")
                || it.packageName.startsWith("com.tencent")
                || it.packageName.startsWith("com.modemdebug")
                || it.packageName.startsWith("com.goodix")
                || it.packageName.startsWith("com.wapi")
                || it.packageName.startsWith("se.dirac")
                || it.packageName.startsWith("com.dsi")
                || it.packageName.startsWith("com.longcheertel")
                || it.applicationInfo?.dataDir?.startsWith("/data/system") == true
    }
}