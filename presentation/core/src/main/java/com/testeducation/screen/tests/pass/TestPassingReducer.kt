package com.testeducation.screen.tests.pass

import com.testeducation.converter.test.answer.toUI
import com.testeducation.core.IReducer
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.screen.tests.pass.TestPassingState

class TestPassingReducer(
    private val answerColorExtractor: IAnswerColorExtractor,
    private val timeConverterLongToString: ITimeConverterLongToString
) : IReducer<TestPassingModelState, TestPassingState> {

    override fun reduce(modelState: TestPassingModelState): TestPassingState {

        return TestPassingState()
    }



}
