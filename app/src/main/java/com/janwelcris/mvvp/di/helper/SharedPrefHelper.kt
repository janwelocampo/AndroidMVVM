
package com.janwelcris.mvvp.di.helper
import android.content.SharedPreferences


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefHelper @Inject
constructor(private val pref: SharedPreferences) {
    private val editor: SharedPreferences.Editor = pref.edit()

    init {
        this.editor.apply()
    }

    fun addData(key: String, data: String): Boolean {
        editor.putString(key, data)
        return editor.commit()
    }

    fun addData(key: String, data: Int): Boolean {
        editor.putInt(key, data)
        return editor.commit()
    }

    fun addData(key: String, data: Float): Boolean {
        editor.putFloat(key, data)
        return editor.commit()
    }

    fun addData(key: String, data: Long): Boolean {
        editor.putLong(key, data)
        return editor.commit()
    }


    fun addData(key: String, data: Boolean): Boolean {
        editor.putBoolean(key, data)
        return editor.commit()
    }

    fun getString(key: String): String? {
        return pref.getString(key, null)
    }

    fun getStringEmptyResponse(key: String): String? {
        return pref.getString(key, " ")
    }

    fun getInteger(key: String): Int {
        return pref.getInt(key, -1)
    }

    fun getLong(key: String): Long {
        return pref.getLong(key, -1)
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return pref.getBoolean(key, defaultValue)
    }

    fun removeData(key: String) {
        editor.remove(key)
        editor.commit()
    }

    fun clearData() {
        editor.clear()
        editor.commit()
    }
}
