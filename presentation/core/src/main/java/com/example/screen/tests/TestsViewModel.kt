package com.example.screen.tests

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.test.GetTests
import com.example.helper.error.IExceptionHandler
import com.example.logic.screen.tests.TestsSideEffect
import com.example.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class TestsViewModel(
    private val router: NavigationRouter,
    private val getTests: GetTests,
    reducer: IReducer<TestsModelState, com.example.logic.screen.tests.TestsState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<TestsModelState, com.example.logic.screen.tests.TestsState, TestsSideEffect>(reducer, errorHandler) {

    override val initialModelState: TestsModelState = TestsModelState()

    init {
        loadTests()
    }

    private fun loadTests() = intent {
        val tests = getTests()
        updateModelState {
            copy(tests = tests)
        }
    }

}
