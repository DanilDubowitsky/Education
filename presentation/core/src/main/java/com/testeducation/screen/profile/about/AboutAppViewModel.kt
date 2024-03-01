package com.testeducation.screen.profile.about

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.profile.about.AboutAppSideEffect
import com.testeducation.logic.screen.profile.about.AboutAppState
import com.testeducation.navigation.core.NavigationRouter

class AboutAppViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<AboutAppModelState, AboutAppState>,
    errorHandler: IExceptionHandler,
) : BaseViewModel<AboutAppModelState, AboutAppState, AboutAppSideEffect>(reducer, errorHandler) {
    override val initialModelState: AboutAppModelState = AboutAppModelState()

    fun close() {
        router.exit()
    }
}