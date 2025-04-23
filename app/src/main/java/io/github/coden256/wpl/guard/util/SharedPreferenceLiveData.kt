package io.github.coden256.wpl.guard.util

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import hu.autsoft.krate.Krate

abstract class SharedPreferenceLiveData<T>(val sharedPrefs: SharedPreferences,
                                           private val key: String) : LiveData<T>() {

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        if (key == this.key) {
            value = getValueFromPreferences()
        }
    }

    abstract fun getValueFromPreferences(): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences()
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}

fun <T : Any> Krate.liveData(key: String, getValue: () -> T): SharedPreferenceLiveData<T> {
    return object : SharedPreferenceLiveData<T>(sharedPreferences, key) {
        override fun getValueFromPreferences(): T {
            return getValue()
        }
    }
}