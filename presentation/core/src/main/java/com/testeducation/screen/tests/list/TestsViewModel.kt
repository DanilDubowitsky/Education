package com.testeducation.screen.tests.list

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.list.TestsSideEffect
import com.testeducation.logic.screen.tests.list.TestsState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class TestsViewModel(
    private val router: NavigationRouter,
    private val getTests: GetTests,
    private val getThemes: GetThemes,
    private val getCurrentUser: GetCurrentUser,
    reducer: IReducer<TestsModelState, TestsState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<TestsModelState, TestsState, TestsSideEffect>(reducer, errorHandler) {

    override val initialModelState: TestsModelState = TestsModelState()

    init {
        loadTests()
        loadThemes()
        loadUserData()
    }

    private fun loadTests() = intent {
        val modelState = getModelState()
        val tests = getTests(
            themeId = modelState.selectedThemeId,
            orderField = modelState.selectedOrderField
        )
        updateModelState {
            copy(
                tests = tests.tests + listOf(
                    TestShort(
                        "ddf",
                        "Test1",
                        2,
                        true,
                        100,
                        1000,
                        ThemeShort("2", "Theme1")
                    ),
                    TestShort(
                        "asda",
                        "Test2",
                        2,
                        true,
                        100,
                        1000,
                        ThemeShort("2", "Theme2")
                    ),
                    TestShort(
                        "ddsf",
                        "Test1",
                        2,
                        true,
                        100,
                        1000,
                        ThemeShort("2", "Theme3")
                    ),
                    TestShort(
                        "ddgdfgf",
                        "Test4",
                        2,
                        true,
                        100,
                        1000,
                        ThemeShort("2", "Theme4")
                    ),
                ),
                testsLoadingState = TestsModelState.TestsLoadingState.IDLE,
            )
        }
    }

    private fun loadThemes() = intent {
        val themes = getThemes()
        updateModelState {
            copy(
                themes = themes,
                themesLoadingState = TestsModelState.ThemesLoadingState.IDLE
            )
        }
    }

    private fun loadUserData() = intent {
        val user = getCurrentUser()
        updateModelState {
            copy(
                user = user,
                profileLoadingState = TestsModelState.ProfileLoadingState.IDLE
            )
        }
    }

    fun onThemeChanged(id: String?) = intent {
        val modelState = getModelState()
        if (modelState.selectedThemeId == id) return@intent
        updateModelState {
            copy(
                tests = emptyList(),
                selectedThemeId = id,
                testsLoadingState = TestsModelState.TestsLoadingState.LOADING
            )
        }
        loadTests()
    }

    fun onScrollToBottom() = intent {
        router.sendResult(NavigationScreen.Main.Tests.OnScrollToBottom, Unit, false)
    }

    fun onScrollToTop() = intent {
        router.sendResult(NavigationScreen.Main.Tests.OnScrollToTop, Unit, false)
    }

    fun openFiltersScreen() = intent {
        val screen = NavigationScreen.Tests.Filters
        router.navigateTo(screen)
    }

}
