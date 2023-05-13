package com.example.education.di.modules.base

import android.content.Context
import com.example.education.app.App
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideApplicationContext(app: App): Context = app.applicationContext
    }

}
