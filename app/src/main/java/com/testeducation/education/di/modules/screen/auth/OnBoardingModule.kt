package com.testeducation.education.di.modules.screen.auth

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.auth.onboarding.OnBoardingState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.auth.onboarding.OnBoardingModelState
import com.testeducation.screen.auth.onboarding.OnBoardingReducer
import com.testeducation.screen.auth.onboarding.OnBoardingViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface OnBoardingModule {
    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    fun bindViewModel(viewModel: OnBoardingViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(resource: IResourceHelper): IReducer<OnBoardingModelState, OnBoardingState> =
            OnBoardingReducer(resource)

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            reducer: IReducer<OnBoardingModelState, OnBoardingState>,
            errorHandler: IExceptionHandler
        ): OnBoardingViewModel = OnBoardingViewModel(
            router = router,
            reducer = reducer,
            errorHandler = errorHandler
        )
    }
}