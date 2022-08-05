package com.janwelcris.mvvp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.janwelcris.mvvp.utils.isNight
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MVVMApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDayNightMode()
    }

    private fun setupDayNightMode() {
        // Get UI mode and set
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}