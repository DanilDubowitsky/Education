package com.testeducation.education.di.modules.domain.usecase.internal

import com.testeducation.domain.cases.internal.IsAppVersionUpdateRequired
import com.testeducation.domain.config.internal.IAppVersionConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object AppVersionUseCaseModule {

    @Provides
    @Reusable
    fun provideIsAppVersionUpdateRequired(
        config: IAppVersionConfig
    ) = IsAppVersionUpdateRequired(config)
}
