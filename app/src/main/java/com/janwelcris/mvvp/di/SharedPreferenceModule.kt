package com.janwelcris.mvvp.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.janwelcris.mvvp.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceModule {

    @SuppressLint("HardwareIds")
    @Singleton
    @Provides
    fun provideSharedPreference(app: Application): SharedPreferences {
        val masterKey = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hash = "/2#P\\Cj-c9A\\4EVV5=~eVV&Yv3qY+Pwu"
            val androidId = Settings.Secure.getString(app.contentResolver, Settings.Secure.ANDROID_ID)

            val spec = KeyGenParameterSpec.Builder(
                "$androidId$hash",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()

            MasterKey.Builder(app,"$androidId$hash")
                .setKeyGenParameterSpec(spec)
                .build()

        } else {
            MasterKey.Builder(app)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        }

        return EncryptedSharedPreferences.create(
            app,
            app.packageName + ".prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}