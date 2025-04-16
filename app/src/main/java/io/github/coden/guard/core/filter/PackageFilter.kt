package io.github.coden.guard.core.filter

import android.content.Context

interface PackageFilter {
    fun isAllowed(context: Context, pkg: String): Boolean
}