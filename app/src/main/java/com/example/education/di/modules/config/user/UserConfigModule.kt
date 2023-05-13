package com.example.education.di.modules.config.user

import com.example.core.config.IConfigSource
import com.example.core.config.UserConfig
import com.example.core.config.UserConfig.Companion.CONFIG_NAME
import com.example.domain.config.IUserConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserConfigModule {

    @Provides
    @Reusable
    fun provideUserConfig(configProvider: IConfigSource.Provider): IUserConfig {
        val configSource = configProvider.provideEncryptedConfigSourceInstance(CONFIG_NAME)
        return UserConfig(configSource)
    }

}
