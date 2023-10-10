package com.testeducation.education.di.components

import com.testeducation.education.di.modules.base.ActivityModule
import com.testeducation.education.di.modules.base.AppModule
import com.testeducation.education.di.modules.config.ConfigModule
import com.testeducation.education.di.modules.config.internal.AppVersionConfigModule
import com.testeducation.education.di.modules.core.repository.question.QuestionCoreModule
import com.testeducation.education.di.modules.core.repository.test.TestCoreModule
import com.testeducation.education.di.modules.core.repository.theme.ThemeCoreModule
import com.testeducation.education.di.modules.core.repository.user.UserCoreModule
import com.testeducation.education.di.modules.domain.interaction.UserInteractorModule
import com.testeducation.education.di.modules.domain.usecase.auth.AuthUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.internal.AppVersionUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.question.QuestionUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.test.TestUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.theme.ThemeUseCaseModule
import com.testeducation.education.di.modules.domain.usecase.user.UserUseCaseModule
import com.testeducation.education.di.modules.helper.HelperModule
import com.testeducation.education.di.modules.local.DataBaseModule
import com.testeducation.education.di.modules.local.theme.ThemeLocalModule
import com.testeducation.education.di.modules.navigation.NavigationModule
import com.testeducation.education.di.modules.remote.client.auth.AuthClientModule
import com.testeducation.education.di.modules.remote.client.refresh.RefreshClientModule
import com.testeducation.education.di.modules.remote.helper.RemoteHelperModule
import com.testeducation.education.di.modules.remote.internal.ApplicationVersionRemoteSourceModule
import com.testeducation.education.di.modules.remote.question.QuestionRemoteModule
import com.testeducation.education.di.modules.remote.retrofit.category.ThemeRetrofitModule
import com.testeducation.education.di.modules.remote.retrofit.global.RetrofitModule
import com.testeducation.education.di.modules.remote.retrofit.internal.BackendRetrofitClientModule
import com.testeducation.education.di.modules.remote.retrofit.refresh.RefreshRetrofitModule
import com.testeducation.education.di.modules.remote.retrofit.test.TestRetrofitClientModule
import com.testeducation.education.di.modules.remote.retrofit.user.UserRetrofitClientModule
import com.testeducation.education.di.modules.remote.test.TestRemoteModule
import com.testeducation.education.di.modules.remote.theme.ThemeRemoteModule
import com.testeducation.education.di.modules.remote.user.UserRemoteModule
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
        DataBaseModule::class,

        // Auth
        AuthClientModule::class,
        AuthServiceModule::class,
        AuthUseCaseModule::class,
        RemoteHelperModule::class,

        // Core
        NavigationModule::class,
        AppModule::class,
        ConfigModule::class,
        RefreshRetrofitModule::class,
        RefreshClientModule::class,
        HelperModule::class,
        UserInteractorModule::class,

        // Test
        TestCoreModule::class,
        TestUseCaseModule::class,
        TestRetrofitClientModule::class,
        TestRemoteModule::class,

        //User
        UserCoreModule::class,
        UserRemoteModule::class,
        UserUseCaseModule::class,
        UserRetrofitClientModule::class,

        //Theme
        ThemeRemoteModule::class,
        ThemeCoreModule::class,
        ThemeRetrofitModule::class,
        ThemeUseCaseModule::class,
        ThemeLocalModule::class,

        // Internal
        BackendRetrofitClientModule::class,
        ApplicationVersionRemoteSourceModule::class,
        AppVersionConfigModule::class,
        AppVersionUseCaseModule::class,

        //Question
        QuestionUseCaseModule::class,
        QuestionCoreModule::class,
        QuestionRemoteModule::class
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