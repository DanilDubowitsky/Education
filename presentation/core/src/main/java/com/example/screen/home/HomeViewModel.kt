package com.example.screen.home

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.helper.error.IExceptionHandler
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class HomeViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<HomeModelState, HomeState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<HomeModelState, HomeState, HomeSideEffect>(
    reducer, errorHandler
) {

    override val initialModelState: HomeModelState = HomeModelState()

    fun navigateToMain() = intent {
        updateModelState {
            copy(navigationItems = HomeModelState.BottomNavigationItems.TESTS)
        }
        val screen = NavigationScreen.Main.Tests
        router.replace(screen, HOME_NAVIGATOR_KEY)
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
        val screen = NavigationScreen.Main.CreationTest
        router.navigateTo(screen)
    }

    companion object {
        const val HOME_NAVIGATOR_KEY = "HOME_NAVIGATOR_KEY"
    }

}
