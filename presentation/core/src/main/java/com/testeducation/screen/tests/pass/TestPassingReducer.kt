package com.testeducation.screen.tests.pass

import com.testeducation.converter.test.question.toUI
import com.testeducation.core.IReducer
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.screen.tests.pass.TestPassingState

class TestPassingReducer(
    private val answerColorExtractor: IAnswerColorExtractor,
    private val timeConverterLongToString: ITimeConverterLongToString
) : IReducer<TestPassingModelState, TestPassingState> {

    override fun reduce(modelState: TestPassingModelState): TestPassingState {
        val questionUI = modelState.currentQuestion?.toUI(
            answerColorExtractor,
            timeConverterLongToString
        )

        return TestPassingState(
            currentQuestion = questionUI
        )
    }

}
