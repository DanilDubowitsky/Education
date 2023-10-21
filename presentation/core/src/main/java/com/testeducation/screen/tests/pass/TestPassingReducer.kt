package com.testeducation.screen.tests.pass

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.pass.TestPassingState

class TestPassingReducer : IReducer<TestPassingModelState, TestPassingState> {

    override fun reduce(modelState: TestPassingModelState): TestPassingState {
        return TestPassingState()
    }
}
