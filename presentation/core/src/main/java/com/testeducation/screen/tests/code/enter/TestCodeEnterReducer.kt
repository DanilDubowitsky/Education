package com.testeducation.screen.tests.code.enter

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.code.enter.TestCodeEnterState

class TestCodeEnterReducer : IReducer<TestCodeEnterModelState, TestCodeEnterState> {

    override fun reduce(modelState: TestCodeEnterModelState): TestCodeEnterState {
        return TestCodeEnterState(isLoading = modelState.isLoading)
    }
}
