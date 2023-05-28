package com.testeducation.education.di.modules.fragment.home

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.home.HomeModelState
import com.testeducation.screen.home.HomeReducer
import com.testeducation.screen.home.HomeState
import com.testeducation.screen.home.HomeViewModel
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
