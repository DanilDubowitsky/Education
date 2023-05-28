package com.example.screen.tests

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.test.GetTests
import com.example.domain.cases.theme.GetThemes
import com.example.domain.cases.user.GetCurrentUser
import com.example.helper.error.IExceptionHandler
import com.example.logic.screen.tests.TestsSideEffect
import com.example.logic.screen.tests.TestsState
import com.example.navigation.core.NavigationRouter
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

    private fun loadTests() = singleIntent(getThemes.javaClass.name) {
        val modelState = getModelState()
        val tests = getTests(
            themeId = modelState.selectedThemeId
        )
        updateModelState {
            copy(
                tests = tests,
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
                selectedThemeId = id,
                testsLoadingState = TestsModelState.TestsLoadingState.LOADING
            )
        }
        loadTests()
    }

}
