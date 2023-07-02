package com.testeducation.screen.home

import android.util.Log
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.home.HomeSideEffect
import com.testeducation.logic.screen.home.HomeState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class HomeViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<HomeModelState, HomeState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<HomeModelState, HomeState, HomeSideEffect>(
    reducer, errorHandler
) {

    override val initialModelState: HomeModelState = HomeModelState()

    init {
        intent {
            postSideEffect(HomeSideEffect.SlideUpNavigation)
            navigateToTests()
        }
    }

    fun navigateToTests() = intent {
        updateModelState {
            copy(navigationItems = HomeModelState.BottomNavigationItems.TESTS)
        }
        val screen = NavigationScreen.Main.Tests
        router.setResultListener(NavigationScreen.Main.Tests.OnScrollToTop, ::onScrollToTop)
        router.setResultListener(NavigationScreen.Main.Tests.OnScrollToBottom, ::onScrollToBottom)
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

    private fun onScrollToTop(unit: Unit) = intent {
        postSideEffect(HomeSideEffect.SlideUpNavigation)
    }

    private fun onScrollToBottom(unit: Unit) = intent {
        postSideEffect(HomeSideEffect.SlideDownNavigation)
    }

    companion object {
        const val HOME_NAVIGATOR_KEY = "HOME_NAVIGATOR_KEY"
    }

}
