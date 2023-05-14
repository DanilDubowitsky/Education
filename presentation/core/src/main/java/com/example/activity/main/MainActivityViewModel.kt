package com.example.activity.main

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.interaction.user.UserConfigInteractor
import com.example.helper.error.IErrorHandler
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class MainActivityViewModel(
    private val router: NavigationRouter,
    private val userConfigInteractor: UserConfigInteractor,
    reducer: IReducer<MainActivityModelState, MainActivityState>,
    errorHandler: IErrorHandler
) : BaseViewModel<MainActivityModelState, MainActivityState, MainActivitySideEffect>(
    reducer,
    errorHandler
) {

    override val initialModelState: MainActivityModelState = MainActivityModelState()

    fun prepare() = intent {
        val isExpired = userConfigInteractor.isRefreshTokenExpired()
        val screen = if (isExpired) NavigationScreen.Auth.Login
        else NavigationScreen.Main.Home
        router.navigateTo(screen)
    }

}
