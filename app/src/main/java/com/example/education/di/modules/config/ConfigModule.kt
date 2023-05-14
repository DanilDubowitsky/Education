package com.example.education.di.modules.config

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.core.config.IConfigSource
import com.example.education.di.modules.config.user.UserConfigModule
import com.example.local.config.ConfigSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module(includes = [
    UserConfigModule::class
])
object ConfigModule {

    @Provides
    @Reusable
    fun provideConfigProvider(context: Context): IConfigSource.Provider =
        object : IConfigSource.Provider {
            override fun provideConfigSourceInstance(name: String?): IConfigSource {
                return ConfigSource(context.getSharedPreferences(name, Context.MODE_PRIVATE))
            }

            override fun provideEncryptedConfigSourceInstance(name: String): IConfigSource {
                val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                val sharedPreferences = EncryptedSharedPreferences.create(
                    name,
                    masterKey,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
                return ConfigSource(sharedPreferences)
            }
        }

}
