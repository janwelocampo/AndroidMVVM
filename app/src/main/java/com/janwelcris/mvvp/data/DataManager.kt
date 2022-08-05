package com.janwelcris.mvvp.data


import com.janwelcris.mvvp.di.helper.SharedPrefHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(private val pref: SharedPrefHelper) {

    var userToken: String?
        get() {
          return  pref.getString(KEY_USER_TOKEN)
        }
        set(value) {
            if (!value.isNullOrEmpty()){
                pref.addData(KEY_USER_TOKEN, value)
            }
        }

    var sessionID: String?
        get() {
            return  pref.getString(KEY_SESSION_ID)
        }
        set(value) {
            if (!value.isNullOrEmpty()){
                pref.addData(KEY_SESSION_ID, value)
            }
        }

    companion object{
        const val KEY_USER_TOKEN = "KEY_USER_TOKEN"
        const val KEY_SESSION_ID = "KEY_SESSION_ID"
    }
}