package com.testeducation.education.di.modules.config.user

import com.testeducation.core.config.IConfigSource
import com.testeducation.core.config.user.RegistrationConfig
import com.testeducation.core.config.user.TokenConfirmConfig
import com.testeducation.core.config.user.UserConfig
import com.testeducation.core.config.user.UserConfig.Companion.CONFIG_NAME
import com.testeducation.domain.config.user.IRegistrationConfig
import com.testeducation.domain.config.user.ITokenConfirmConfig
import com.testeducation.domain.config.user.IUserConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserConfigModule {

    @Provides
    @Reusable
    fun provideUserConfig(configProvider: IConfigSource.Provider): IUserConfig {
        val configSource = configProvider.provideConfigSourceInstance(CONFIG_NAME)
        return UserConfig(configSource)
    }

    @Provides
    @Reusable
    fun provideRegistrationConfig(configProvider: IConfigSource.Provider): IRegistrationConfig {
        val configSource = configProvider.provideConfigSourceInstance(RegistrationConfig.NAME)
        return RegistrationConfig(configSource)
    }

    @Provides
    @Reusable
    fun provideConfirmConfig(configProvider: IConfigSource.Provider): ITokenConfirmConfig {
        val configSource = configProvider.provideConfigSourceInstance(TokenConfirmConfig.NAME)
        return TokenConfirmConfig(configSource)
    }
}
