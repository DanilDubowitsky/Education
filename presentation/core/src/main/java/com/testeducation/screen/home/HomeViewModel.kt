package com.testeducation.screen.home

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.home.HomeSideEffect
import com.testeducation.logic.screen.home.HomeState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.StringUtils.isNotEmptyOrBlank
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
            copy(selectedScreen = HomeModelState.BottomNavigationItems.TESTS)
        }
        val screen = NavigationScreen.Main.Tests
        router.setResultListener(NavigationScreen.Main.Tests.OnScrollToTop, ::onScrollToTop)
        router.setResultListener(NavigationScreen.Main.Tests.OnScrollToBottom, ::onScrollToBottom)
        router.replace(screen, HOME_NAVIGATOR_KEY)
    }


    fun navigateToFavorites() = intent {
        val currentScreen = getModelState().selectedScreen
        val screen = NavigationScreen.Main.LikedTests
        if (currentScreen == HomeModelState.BottomNavigationItems.FAVORITES) return@intent
        updateModelState {
            copy(selectedScreen = HomeModelState.BottomNavigationItems.FAVORITES)
        }
        router.replace(screen, HOME_NAVIGATOR_KEY)
    }

    fun navigateToProfile() = intent {
        updateModelState {
            copy(selectedScreen = HomeModelState.BottomNavigationItems.PROFILE)
        }
    }

    fun navigateToSettings() = intent {
        updateModelState {
            copy(selectedScreen = HomeModelState.BottomNavigationItems.SETTINGS)
        }
    }

    fun navigateToCreation() = intent {
        val screen = NavigationScreen.Main.CreationTest
        router.setResultListener(NavigationScreen.Main.OnCreationTestResult) { idTest ->
            if (idTest.isNotEmptyOrBlank()) {
                val screenSelectionQuestion = NavigationScreen.Main.SelectionTest(idTest)
                router.navigateTo(screenSelectionQuestion)
            }
        }
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
