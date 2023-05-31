package com.testeducation.education.di.modules.domain.interaction

import com.testeducation.domain.config.IUserConfig
import com.testeducation.domain.interaction.user.UserConfigInteractor
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
