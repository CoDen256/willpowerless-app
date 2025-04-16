package io.github.coden.guard.filter

import android.content.Context

interface PackageFilter {
    fun isAllowed(context: Context, pkg: String): Boolean
}