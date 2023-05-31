package com.testeducation.screen.home

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
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
