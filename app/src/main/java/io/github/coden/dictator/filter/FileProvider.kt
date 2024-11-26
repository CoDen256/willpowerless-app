package io.github.coden.dictator.filter

import android.content.Context
import java.io.InputStream

fun interface FileProvider {
    fun get(context: Context): InputStream
}