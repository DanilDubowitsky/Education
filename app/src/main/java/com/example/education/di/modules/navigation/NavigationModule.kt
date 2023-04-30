package com.example.education.di.modules.navigation

import com.example.education.screen.ScreenAdapter
import com.example.navigation.core.AndroidNavigationRouter
import com.example.navigation.core.IScreenAdapter
import com.example.navigation.core.NavigationHost
import com.example.navigation.core.NavigationRouter
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
            host: NavigationHost
        ): NavigationRouter = AndroidNavigationRouter(host)
    }

}
