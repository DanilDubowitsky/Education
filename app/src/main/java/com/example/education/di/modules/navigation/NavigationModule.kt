package com.example.education.di.modules.navigation

import com.example.education.screen.ScreenAdapter
import com.example.navigation.core.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface NavigationModule {

    companion object {

        @Provides
        @Singleton
        fun provideNavigationHost(): NavigationHost = NavigationHost()

        @Provides
        @Singleton
        fun provideScreenAdapter(): IScreenAdapter = ScreenAdapter()

        @Provides
        @Singleton
        fun provideNavigationRouter(
            host: NavigationHost,
            resultWire: IResultWire
        ): NavigationRouter = AndroidNavigationRouter(host, resultWire)

        @Provides
        @Singleton
        fun provideResultWire(): IResultWire = ResultWire()
    }

}
