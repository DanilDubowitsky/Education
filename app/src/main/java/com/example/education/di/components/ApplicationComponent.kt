package com.example.education.di.components

import com.example.education.app.App
import com.example.education.di.modules.base.ActivityModule
import com.example.education.di.modules.base.AppModule
import com.example.education.di.modules.config.ConfigModule
import com.example.education.di.modules.domain.interaction.UserInteractorModule
import com.example.education.di.modules.domain.repository.test.TestRepositoryModule
import com.example.education.di.modules.domain.repository.user.UserRepositoryModule
import com.example.education.di.modules.domain.usecase.auth.AuthUseCaseModule
import com.example.education.di.modules.domain.usecase.test.TestUseCaseModule
import com.example.education.di.modules.domain.usecase.user.UserUseCaseModule
import com.example.education.di.modules.helper.ResourceHelperModule
import com.example.education.di.modules.navigation.NavigationModule
import com.example.education.di.modules.remote.client.auth.AuthClientModule
import com.example.education.di.modules.remote.client.refresh.RefreshClientModule
import com.example.education.di.modules.remote.retrofit.refresh.RefreshRetrofitModule
import com.example.education.di.modules.remote.retrofit.global.RetrofitModule
import com.example.education.di.modules.remote.retrofit.test.TestRetrofitClientModule
import com.example.education.di.modules.remote.retrofit.user.UserRetrofitClientModule
import com.example.education.di.modules.remote.source.test.TestRemoteSourceModule
import com.example.education.di.modules.remote.source.user.UserRemoteSourceModule
import com.example.education.di.modules.service.auth.AuthServiceModule
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
        NavigationModule::class,
        AppModule::class,
        ConfigModule::class,
        RefreshRetrofitModule::class,
        RefreshClientModule::class,
        ResourceHelperModule::class,
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
        UserRetrofitClientModule::class
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: App)

}