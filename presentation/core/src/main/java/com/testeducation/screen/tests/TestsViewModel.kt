package com.testeducation.screen.tests

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.TestsSideEffect
import com.testeducation.logic.screen.tests.TestsState
import com.testeducation.navigation.core.NavigationRouter
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
            val tests = testsDeferred.await()
            val user = userDeferred.await()
            val themes = getThemes()
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
