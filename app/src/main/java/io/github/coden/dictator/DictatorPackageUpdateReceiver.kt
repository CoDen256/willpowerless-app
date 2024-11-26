package io.github.coden.dictator

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import io.github.coden.dictator.Owner.Companion.asOwner
import io.github.coden.dictator.filter.FilePackageFilter
import io.github.coden.dictator.filter.FileProvider


class DictatorPackageUpdateReceiver : BroadcastReceiver() {

    val provider = FileProvider {
        it.resources.openRawResource(R.raw.blocked)
    }
    val filter = FilePackageFilter(provider)


    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("DictatorPackageReceiver", "Got broadcast: $intent...")

        val data = intent?.data ?: return
        val packageName = data.schemeSpecificPart
        Log.i("DictatorPackageReceiver", "$packageName added, verifying...")

        val ctx = context ?: return
        Log.i("DictatorPackageUpdateReceiver", "Checking filter")

        val allowed = filter.isAllowed(ctx, packageName)
        Log.i("DictatorPackageUpdateReceiver", "$packageName allowed?: $allowed")

        if (!allowed){
            asOwner(ctx) {
                Log.i("DictatorPackageUpdateReceiver", "Uh-oh, hiding $packageName")
                hide(packageName, false)
            }
        }

    }

}