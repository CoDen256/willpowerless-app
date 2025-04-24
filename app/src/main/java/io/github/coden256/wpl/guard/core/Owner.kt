package io.github.coden256.wpl.guard.core

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import io.github.coden256.wpl.guard.services.AdminReceiver
import java.util.concurrent.Executors

class Owner(private val context: Context) {
    private val executor = Executors.newSingleThreadExecutor()
    private val devicePolicyManager = context.getSystemService(DevicePolicyManager::class.java)
    private val adminComponent = ComponentName(context, AdminReceiver::class.java)
    val isAdmin: Boolean = devicePolicyManager.isDeviceOwnerApp(context.packageName)
    private val tag = "GuardOwner"

    init {
        verify("initialize owner")
    }

    fun run(action: DevicePolicyManager.()->(Unit)): DevicePolicyManager?{
        devicePolicyManager?.action()
        return devicePolicyManager
    }

    //e.g. key=UserManager.DISALLOW_CONFIG_VPN
    fun clearUserRestriction(key: String){
        devicePolicyManager.clearUserRestriction(adminComponent, key)
    }

    fun hide(pkg: String, hide: Boolean=true){
        Log.i(tag, "hiding $pkg? $hide")
//        devicePolicyManager.setApplicationHidden(adminComponent, pkg, hide)
    }

    fun blockUninstall(pkg: String, block: Boolean=true){
        Log.i(tag, "blocking uninstall $pkg? $block")
//        devicePolicyManager.setUninstallBlocked(adminComponent, pkg, block)
    }

    fun enableBackupService(enable:Boolean=true){
        Log.i(tag, "enabling backup? $enable")
        devicePolicyManager.setBackupServiceEnabled(adminComponent, enable)
    }

    fun setAlwaysOnVpn(pkg: String){
        Log.i(tag, "set always on: $pkg")
//        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, pkg, true)
    }

    fun removeAlwaysOnVpn(){
        Log.i(tag, "remove always on vpn")
//        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, null, false)
    }

    fun transferOwnership(pkg: String, adminReceiver: String){
        Log.i(tag, "Transfering ownership to $pkg/$adminReceiver")
        val targetAdmin = ComponentName(pkg, "$pkg$adminReceiver")
//        devicePolicyManager.transferOwnership(adminComponent, targetAdmin, null)
    }

    fun uninstall(packageName: String) {
        Log.i(tag, "Uninstalling $packageName...")

        // Check if the app is installed
        val packageManager = context.packageManager
        try {
            packageManager.getPackageInfo(packageName, 0)

            // If installed, clear app data (optional)
            devicePolicyManager.clearApplicationUserData(adminComponent, packageName, executor) { s, success ->
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
            Log.i(tag, "Not an admin, operation denied: <$op>")
            Toast.makeText(context, "Not an admin, sorry :(", Toast.LENGTH_LONG).show()
            throw IllegalStateException("The ${context.packageName} is not an admin")
        }
    }

    companion object {
        fun asOwner(ctx: Context, action: Owner.()->(Unit)){
            return action(Owner(ctx))
        }
    }
}