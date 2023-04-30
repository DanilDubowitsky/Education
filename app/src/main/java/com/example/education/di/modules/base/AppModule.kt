package com.example.education.di.modules.base

import android.content.Context
import com.example.education.app.App
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppModule {

    @Binds
    @Singleton
    fun bindApplicationContext(app: App): Context
}
