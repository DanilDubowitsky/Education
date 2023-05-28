package com.testeducation.screen.tests

import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.TestsState

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
