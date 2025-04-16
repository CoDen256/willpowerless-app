package io.github.coden.guard.filter

import android.content.Context
import java.io.InputStream
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class FilePackageFilter(private val provider: FileProvider): PackageFilter {

    private val read: AtomicBoolean = AtomicBoolean(false)
    private lateinit var blocked: List<Regex>

    override fun isAllowed(context: Context, pkg: String): Boolean {
        return matchesNone(pkg, provider.readBlocked(context))
    }

    private fun matchesNone(input: String, regexes: List<Regex>): Boolean {
        return regexes.none { it.containsMatchIn(input) }
    }

    private fun FileProvider.readBlocked(context: Context): List<Regex>{
        if (!read.getAndSet(true)){
            blocked = get(context).readRegexList()
        }
        return blocked
    }

    private fun InputStream.readRegexList(): List<Regex>{
        return bufferedReader(Charsets.UTF_8)
            .lineSequence()
            .map { Regex(it, RegexOption.IGNORE_CASE) }
            .toList()
    }

}