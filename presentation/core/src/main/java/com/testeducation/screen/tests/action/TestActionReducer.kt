package com.testeducation.screen.tests.action

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.action.TestActionState

class TestActionReducer : IReducer<TestActionModelState, TestActionState> {

    override fun reduce(modelState: TestActionModelState): TestActionState {
        return TestActionState(
            testTitle = modelState.testTitle,
            owner = modelState.isOwner,
            passed = modelState.isPassed
        )
    }
}
