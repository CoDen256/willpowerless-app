package io.github.coden.dictator.filter

import android.content.Context

interface PackageFilter {
    fun isAllowed(context: Context, pkg: String): Boolean
}