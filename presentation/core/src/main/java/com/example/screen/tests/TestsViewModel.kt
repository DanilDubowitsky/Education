package com.example.screen.tests

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.test.GetTests
import com.example.helper.error.IErrorHandler
import org.orbitmvi.orbit.syntax.simple.intent

class TestsViewModel(
    private val getTests: GetTests,
    reducer: IReducer<TestsModelState, TestsState>,
    errorHandler: IErrorHandler
) : BaseViewModel<TestsModelState, TestsState, TestsSideEffect>(reducer, errorHandler) {

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
