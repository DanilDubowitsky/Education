package com.example.screen.tests

import com.example.converter.test.toUIModels
import com.example.core.IReducer
import com.example.logic.screen.tests.TestsState

class TestsReducer : IReducer<TestsModelState, TestsState> {

    override fun reduce(modelState: TestsModelState): TestsState {
        return TestsState(
            tests = modelState.tests.toUIModels(),
            isLoading = modelState.loadingState == TestsModelState.LoadingState.LOADING,
            userName = modelState.user?.userName.orEmpty(),
            themes = modelState.themes.toUIModels()
        )
    }

}
