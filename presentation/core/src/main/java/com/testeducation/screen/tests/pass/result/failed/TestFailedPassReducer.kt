package com.testeducation.screen.tests.pass.result.failed

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.pass.result.failed.TestFailedPassState

class TestFailedPassReducer : IReducer<TestFailedPassModelState, TestFailedPassState> {

    override fun reduce(modelState: TestFailedPassModelState): TestFailedPassState {
        return TestFailedPassState(isCheating = modelState.isCheating)
    }
}
