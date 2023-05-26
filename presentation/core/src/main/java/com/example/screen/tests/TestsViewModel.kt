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
import kotlinx.coroutines.async
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
    }

    private fun loadTests() = intent {
        launchJob {
            val testsDeferred = async { getTests() }
            val userDeferred = async { getCurrentUser() }
            val themesDeferred = async { getThemes() }
            val tests = testsDeferred.await()
            val user = userDeferred.await()
            val themes = themesDeferred.await()
            updateModelState {
                copy(
                    tests = tests,
                    user = user,
                    themes = themes,
                    loadingState = TestsModelState.LoadingState.IDLE
                )
            }
        }
    }

}
