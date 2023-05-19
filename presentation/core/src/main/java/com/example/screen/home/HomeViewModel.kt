package com.example.screen.home

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.helper.error.IErrorHandler
import com.example.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class HomeViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<HomeModelState, HomeState>,
    errorHandler: IErrorHandler
) : BaseViewModel<HomeModelState, HomeState, HomeSideEffect>(
    reducer, errorHandler
) {

    override val initialModelState: HomeModelState = HomeModelState()

    fun navigateToMain() = intent {
        updateModelState {
            copy(navigationItems = HomeModelState.BottomNavigationItems.MAIN)
        }
    }

    fun navigateToFavorites() = intent {
        updateModelState {
            copy(navigationItems = HomeModelState.BottomNavigationItems.FAVORITES)
        }
    }

    fun navigateToProfile() = intent {
        updateModelState {
            copy(navigationItems = HomeModelState.BottomNavigationItems.PROFILE)
        }
    }

    fun navigateToSettings() = intent {
        updateModelState {
            copy(navigationItems = HomeModelState.BottomNavigationItems.SETTINGS)
        }
    }

    fun navigateToCreation() = intent {

    }

    companion object {
        const val HOME_NAVIGATOR_KEY = "HOME_NAVIGATOR_KEY"
    }

}
