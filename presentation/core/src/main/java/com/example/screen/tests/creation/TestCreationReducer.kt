package com.example.screen.tests.creation

import com.example.converter.test.toUIModels
import com.example.core.IReducer
import com.example.logic.screen.tests.TestsState
import com.example.logic.screen.tests.creation.TestCreationState
import com.example.screen.tests.TestsModelState

class TestCreationReducer: IReducer<TestCreationModelState, TestCreationState> {

    override fun reduce(modelState: TestCreationModelState): TestCreationState {
        return TestCreationState(
            isLoading = modelState.loadingState == TestCreationModelState.LoadingState.LOADING,
            themes = modelState.themes.toUIModels()
        )
    }

}
