package com.example.activity.main

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen

class MainActivityViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<MainActivityModelState, MainActivityState>
) : BaseViewModel<MainActivityModelState, MainActivityState, MainActivitySideEffect>(
    reducer
) {

    override val initialModelState: MainActivityModelState = MainActivityModelState()

    fun navigateToRegistration() {
        router.replace(NavigationScreen.Auth.Registration)
    }

}
