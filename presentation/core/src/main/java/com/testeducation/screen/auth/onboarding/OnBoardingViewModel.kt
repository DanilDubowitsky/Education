package com.testeducation.screen.auth.onboarding

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.auth.onboarding.OnBoardingSideEffect
import com.testeducation.logic.screen.auth.onboarding.OnBoardingState
import com.testeducation.navigation.core.NavigationRouter

class OnBoardingViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<OnBoardingModelState, OnBoardingState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<OnBoardingModelState, OnBoardingState, OnBoardingSideEffect>(reducer, errorHandler) {
    override val initialModelState: OnBoardingModelState = OnBoardingModelState()

    fun exit() {
        router.exit()
    }
}