package com.testeducation.education.di.components

import com.testeducation.education.di.modules.base.ActivityModule
import com.testeducation.education.di.modules.base.AppModule
import com.testeducation.education.di.modules.config.ConfigModule
import com.testeducation.education.di.modules.config.internal.AppVersionConfigModule
import com.testeducation.education.di.modules.domain.interaction.UserInteractorModule
import com.testeducation.education.di.modules.domain.repository.test.TestRepositoryModule
import com.testeducation.education.di.modules.domain.repository.theme.ThemeRepositoryModule
import com.testeducation.education.di.modules.domain.repository.user.UserRepositoryModule
import com.testeducation.education.di.modules.domain.usecase.auth.AuthUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.internal.AppVersionUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.test.TestUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.theme.ThemeUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.user.UserUseCaseModule
import com.testeducation.education.di.modules.helper.HelperModule
import com.testeducation.education.di.modules.navigation.NavigationModule
import com.testeducation.education.di.modules.remote.client.auth.AuthClientModule
import com.testeducation.education.di.modules.remote.client.refresh.RefreshClientModule
import com.testeducation.education.di.modules.remote.retrofit.category.ThemeRetrofitModule
import com.testeducation.education.di.modules.remote.retrofit.global.RetrofitModule
import com.testeducation.education.di.modules.remote.retrofit.internal.BackendRetrofitClientModule
import com.testeducation.education.di.modules.remote.retrofit.refresh.RefreshRetrofitModule
import com.testeducation.education.di.modules.remote.retrofit.test.TestRetrofitClientModule
import com.testeducation.education.di.modules.remote.retrofit.user.UserRetrofitClientModule
import com.testeducation.education.di.modules.remote.source.internal.ApplicationVersionRemoteSourceModule
import com.testeducation.education.di.modules.remote.source.test.TestRemoteSourceModule
import com.testeducation.education.di.modules.remote.source.theme.ThemeRemoteSourceModule
import com.testeducation.education.di.modules.remote.source.user.UserRemoteSourceModule
import com.testeducation.education.di.modules.service.auth.AuthServiceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        RetrofitModule::class,

        // Auth
        AuthClientModule::class,
        AuthServiceModule::class,
        AuthUseCaseModule::class,

        // Core
        NavigationModule::class,
        AppModule::class,
        ConfigModule::class,
        RefreshRetrofitModule::class,
        RefreshClientModule::class,
        HelperModule::class,
        UserInteractorModule::class,

        // Test
        TestRepositoryModule::class,
        TestUseCaseModule::class,
        TestRetrofitClientModule::class,
        TestRemoteSourceModule::class,

        //User
        UserRepositoryModule::class,
        UserRemoteSourceModule::class,
        UserUseCaseModule::class,
        UserRetrofitClientModule::class,

        //Theme
        ThemeRemoteSourceModule::class,
        ThemeRepositoryModule::class,
        ThemeRetrofitModule::class,
        ThemeUseCaseModule::class,

        // Internal
        BackendRetrofitClientModule::class,
        ApplicationVersionRemoteSourceModule::class,
        AppVersionConfigModule::class,
        AppVersionUseCaseModule::class
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<com.testeducation.education.app.App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: com.testeducation.education.app.App): com.testeducation.education.di.components.ApplicationComponent.Builder

        fun build(): com.testeducation.education.di.components.ApplicationComponent
    }

    override fun inject(application: com.testeducation.education.app.App)

}