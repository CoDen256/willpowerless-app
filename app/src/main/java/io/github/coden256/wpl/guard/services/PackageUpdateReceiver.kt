package io.github.coden256.wpl.guard.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PACKAGE_ADDED
import android.util.Log
import io.github.coden256.wpl.guard.R
import io.github.coden256.wpl.guard.core.filter.FilePackageFilter
import io.github.coden256.wpl.guard.core.filter.FileProvider


class PackageUpdateReceiver : BroadcastReceiver() {

    val provider = FileProvider {
        it.resources.openRawResource(R.raw.blocked)
    }
    val filter = FilePackageFilter(provider)


    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("GuardPackageUpdateReceiver", "Got package broadcast: $intent...")

        if (intent?.action == ACTION_PACKAGE_ADDED){
            val packageName = intent.data?.schemeSpecificPart
            Log.i("GuardPackageUpdateReceiver", "$packageName added")
        }

//
//        val ctx = context ?: return
//        Log.i("GuardPackageUpdateReceiver", "Checking filter")
//
//        val allowed = filter.isAllowed(ctx, packageName)
//        Log.i("GuardPackageUpdateReceiver", "$packageName allowed?: $allowed")
//
//        if (!allowed){
//            asOwner(ctx) {
//                Log.i("GuardPackageUpdateReceiver", "Uh-oh, hiding $packageName")
////                hide(packageName)
//            }
//        }

    }

}