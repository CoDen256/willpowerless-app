package io.github.coden256.wpl.guard.util

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import hu.autsoft.krate.Krate
import kotlin.reflect.KMutableProperty0

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

fun <T : Any> Krate.liveData(property: KMutableProperty0<T>): SharedPreferenceLiveData<T> {
    return object : SharedPreferenceLiveData<T>(sharedPreferences, property.name) {
        override fun getValueFromPreferences(): T {
            return property.get()
        }
    }
}