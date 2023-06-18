package com.testeducation.education.di.modules.config.internal

import com.testeducation.core.app.IAppBuildConfigHelper
import com.testeducation.core.config.IConfigSource
import com.testeducation.core.config.internal.AppVersionConfig
import com.testeducation.core.config.internal.AppVersionConfig.Companion.APP_VERSION_CONFIG_NAME
import com.testeducation.core.source.remote.internal.IAppVersionRemoteSource
import com.testeducation.domain.config.internal.IAppVersionConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object AppVersionConfigModule {

    @Provides
    @Reusable
    fun provideAppVersionConfig(
        configProvider: IConfigSource.Provider,
        appUpdateRemoteSource: IAppVersionRemoteSource,
        buildConfigHelper: IAppBuildConfigHelper
    ): IAppVersionConfig {
        val configSource = configProvider.provideConfigSourceInstance(APP_VERSION_CONFIG_NAME)
        return AppVersionConfig(configSource, appUpdateRemoteSource, buildConfigHelper)
    }
}
