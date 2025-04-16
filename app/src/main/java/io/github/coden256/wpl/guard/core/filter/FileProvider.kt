package io.github.coden256.wpl.guard.core.filter

import android.content.Context
import java.io.InputStream

fun interface FileProvider {
    fun get(context: Context): InputStream
}