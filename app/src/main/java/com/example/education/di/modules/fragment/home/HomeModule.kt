package com.example.education.di.modules.fragment.home

import androidx.lifecycle.ViewModel
import com.example.core.IReducer
import com.example.education.di.viewmodel.ViewModelKey
import com.example.helper.error.IExceptionHandler
import com.example.navigation.core.NavigationRouter
import com.example.screen.home.HomeModelState
import com.example.screen.home.HomeReducer
import com.example.screen.home.HomeState
import com.example.screen.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindViewModel(viewModel: HomeViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<HomeModelState, HomeState> =
            HomeReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            reducer: IReducer<HomeModelState, HomeState>,
            errorHandler: IExceptionHandler
        ): HomeViewModel = HomeViewModel(
            router,
            reducer,
            errorHandler
        )
    }

}
