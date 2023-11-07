package com.testeducation.screen.tests.pass.result

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.pass.result.TestPassResultState

class TestPassResultReducer : IReducer<TestPassResultModelState, TestPassResultState> {

    override fun reduce(modelState: TestPassResultModelState): TestPassResultState {
        return TestPassResultState(
            trueAnswersCount = modelState.correctAnswers,
            falseAnswersCount = modelState.incorrectAnswers,
            isSuccess = modelState.isSuccess
        )
    }
}
