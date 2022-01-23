package com.igzafer.newsapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreference {companion object {
    private var sharedPreferences: SharedPreferences? = null
    private val ZAMAN="zaman"
    @Volatile
    private var instance: SharedPreference? = null
    private val lock = Any()

    operator fun invoke(context: Context): SharedPreference =
        instance ?: synchronized(lock) {
            instance ?: ozelSharedPreferencesYap(context).also {
                instance = it
            }
        }

    private fun ozelSharedPreferencesYap(context: Context): SharedPreference {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return SharedPreference()
    }
}

    fun zamaniKaydet(zaman: Long) {
        sharedPreferences?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }
    fun zamaniAl()= sharedPreferences?.getLong(ZAMAN,0)
}