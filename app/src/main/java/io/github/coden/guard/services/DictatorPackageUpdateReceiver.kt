package io.github.coden.guard.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.github.coden.guard.core.Owner.Companion.asOwner
import io.github.coden.guard.R
import io.github.coden.guard.core.filter.FilePackageFilter
import io.github.coden.guard.core.filter.FileProvider


class GuardPackageUpdateReceiver : BroadcastReceiver() {

    val provider = FileProvider {
        it.resources.openRawResource(R.raw.blocked)
    }
    val filter = FilePackageFilter(provider)


    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("GuardPackageReceiver", "Got broadcast: $intent...")

        val data = intent?.data ?: return
        val packageName = data.schemeSpecificPart
        Log.i("GuardPackageReceiver", "$packageName added, verifying...")

        val ctx = context ?: return
        Log.i("GuardPackageUpdateReceiver", "Checking filter")

        val allowed = filter.isAllowed(ctx, packageName)
        Log.i("GuardPackageUpdateReceiver", "$packageName allowed?: $allowed")

        if (!allowed){
            asOwner(ctx) {
                Log.i("GuardPackageUpdateReceiver", "Uh-oh, hiding $packageName")
//                hide(packageName)
            }
        }

    }

}