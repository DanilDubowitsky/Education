package com.testeducation.education.di.modules.base

import android.content.Context
import com.testeducation.education.app.App
import com.testeducation.helper.error.ExceptionHandler
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.navigation.core.NavigationRouter
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface AppModule {

    @Binds
    @Singleton
    fun provideApplicationContext(app: App): Context

    companion object {

        @Provides
        fun provideErrorHandler(router: NavigationRouter): IExceptionHandler = ExceptionHandler(router)

    }

}
