package com.example.education.di.modules.base

import android.content.Context
import com.example.education.app.App
import com.example.helper.error.ExceptionHandler
import com.example.helper.error.IExceptionHandler
import com.example.navigation.core.NavigationRouter
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
