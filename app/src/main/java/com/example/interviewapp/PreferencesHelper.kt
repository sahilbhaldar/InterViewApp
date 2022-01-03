package com.example.interviewapp

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper private constructor(private var preferences: SharedPreferences) {


    companion object {

        const val NAME = "spname"
        const val NUMBER = "spnumber"
        const val ISLOGIN = "splogin"
        const val ADDRESS = "spaddress"
        const val LATITUDE = "splat"
        const val LONGITUDE = "splong"

        private var INSTANCE: PreferencesHelper? = null

        fun getInstance(context: Context): PreferencesHelper =
            INSTANCE ?: buildPreferences(context).also { INSTANCE = it }

        private fun buildPreferences(context: Context) =
            PreferencesHelper(
                preferences = context.getSharedPreferences(
                    "RemoteAssistSp",
                    Context.MODE_PRIVATE
                )
            )
    }


    fun setString(key: String, `val`: String?) {
        preferences.edit().putString(key, `val`).apply()
    }

    fun getString(key: String, defaultVal: String? = ""): String? {
        return preferences.getString(key, defaultVal)
    }


    fun setDouble(key: String, `val`: Long) {
        preferences.edit().putLong(key, `val`).apply()
    }

    fun getDouble(key: String, defaultVal: Long): Long {
        return preferences.getLong(key, defaultVal)
    }


    fun setBoolean(key: String, `val`: Boolean) {
        preferences.edit().putBoolean(key, `val`).apply()
    }

    fun getBoolean(key: String, defaultVal: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultVal)
    }


    fun clearAll() {
        preferences.edit().clear().apply()
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

}