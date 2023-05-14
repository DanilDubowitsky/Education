package com.example.education.di.modules.domain.interaction

import com.example.domain.config.IUserConfig
import com.example.domain.interaction.user.UserConfigInteractor
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserInteractorModule {

    @Provides
    @Reusable
    fun provideUserConfigInteractor(
        userConfig: IUserConfig
    ): UserConfigInteractor = UserConfigInteractor(userConfig)

}
