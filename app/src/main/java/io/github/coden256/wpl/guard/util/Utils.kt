package io.github.coden256.wpl.guard.util

import android.content.Context
import android.content.pm.PackageInfo
import androidx.work.ListenableWorker.Result

fun kotlin.Result<*>.asWorkResult(retryIf: (Throwable) -> Boolean = {false}): Result {
    return when {
        this.isSuccess -> Result.success()
        this.isFailure && retryIf(this.exceptionOrNull()!!) -> Result.retry()
        else -> Result.failure()
    }
}

fun Context.getInstalledPackages(): List<PackageInfo> {
    return packageManager
        .getInstalledPackages(0) // 0 for basic info
        .filterNot(systemFilter(this))
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
            || it.applicationInfo?.dataDir?.startsWith("/data/system") == true
}