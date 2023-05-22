package com.example.education.di.modules.activity

import androidx.lifecycle.ViewModel
import com.example.activity.main.MainActivityModelState
import com.example.activity.main.MainActivityReducer
import com.example.activity.main.MainActivityState
import com.example.activity.main.MainActivityViewModel
import com.example.core.IReducer
import com.example.domain.interaction.user.UserConfigInteractor
import com.example.education.di.viewmodel.ViewModelKey
import com.example.helper.error.IExceptionHandler
import com.example.navigation.core.NavigationRouter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindViewModel(viewModel: MainActivityViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<MainActivityModelState, MainActivityState> =
            MainActivityReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            userConfigInteractor: UserConfigInteractor,
            reducer: IReducer<MainActivityModelState, MainActivityState>,
            errorHandler: IExceptionHandler
        ): MainActivityViewModel = MainActivityViewModel(
            router,
            userConfigInteractor,
            reducer,
            errorHandler
        )
    }
}
