package com.example.education.di.components

import com.example.education.app.App
import com.example.education.di.modules.base.ActivityModule
import com.example.education.di.modules.base.AppModule
import com.example.education.di.modules.config.ConfigModule
import com.example.education.di.modules.domain.interaction.UserInteractorModule
import com.example.education.di.modules.domain.usecase.auth.AuthUseCaseModule
import com.example.education.di.modules.helper.ResourceHelperModule
import com.example.education.di.modules.navigation.NavigationModule
import com.example.education.di.modules.remote.client.auth.AuthClientModule
import com.example.education.di.modules.remote.client.refresh.RefreshClientModule
import com.example.education.di.modules.remote.retrofit.RefreshRetrofitModule
import com.example.education.di.modules.remote.retrofit.RetrofitModule
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
        AuthClientModule::class,
        AuthServiceModule::class,
        AuthUseCaseModule::class,
        NavigationModule::class,
        AppModule::class,
        ConfigModule::class,
        RefreshRetrofitModule::class,
        RefreshClientModule::class,
        ResourceHelperModule::class,
        UserInteractorModule::class
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