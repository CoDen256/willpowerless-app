package io.github.coden256.wpl.guard.core.filter

import android.content.Context

interface PackageFilter {
    fun isAllowed(context: Context, pkg: String): Boolean
}