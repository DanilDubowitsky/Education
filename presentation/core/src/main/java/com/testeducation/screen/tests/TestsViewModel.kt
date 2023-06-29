package com.testeducation.screen.tests

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.list.TestsSideEffect
import com.testeducation.logic.screen.tests.list.TestsState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.tests.list.TestsModelState
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

    private fun loadTests() = singleIntent(getThemes.javaClass.name) {
        val modelState = getModelState()
        val tests = getTests(
            themeId = modelState.selectedThemeId
        )
        updateModelState {
            copy(
                tests = tests.tests,
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

}
