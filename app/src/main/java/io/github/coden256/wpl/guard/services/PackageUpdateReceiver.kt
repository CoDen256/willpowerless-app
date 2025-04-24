package io.github.coden256.wpl.guard.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PACKAGE_ADDED
import android.util.Log
import io.github.coden256.wpl.guard.controllers.AppController
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PackageUpdateReceiver : BroadcastReceiver(), KoinComponent {

    private val appController by inject<AppController>()

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("GuardPackageUpdateReceiver", "Got package update: $intent...")

        when (intent.action) {
            ACTION_PACKAGE_ADDED -> onPackageAdded(
                context,
                intent.data?.schemeSpecificPart ?: return
            )
        }
    }

    private fun onPackageAdded(context: Context, pkg: String) {
        Log.i("GuardPackageUpdateReceiver", "Added package: $pkg")
        appController.onNewApp(pkg)
    }
}