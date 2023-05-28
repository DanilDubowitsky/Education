package com.testeducation.activity.main

import com.testeducation.logic.activity.MainActivityState
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.interaction.user.UserConfigInteractor
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.activity.MainActivitySideEffect
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class MainActivityViewModel(
    private val router: NavigationRouter,
    private val userConfigInteractor: UserConfigInteractor,
    reducer: IReducer<MainActivityModelState, MainActivityState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<MainActivityModelState, MainActivityState, MainActivitySideEffect>(
    reducer,
    errorHandler
) {

    override val initialModelState: MainActivityModelState = MainActivityModelState()

    fun prepare() = intent {
        val isExpired = userConfigInteractor.isRefreshTokenExpired()
        val screen = if (isExpired) NavigationScreen.Auth.Login
        else NavigationScreen.Main.Home
        router.replace(screen)
    }

}
