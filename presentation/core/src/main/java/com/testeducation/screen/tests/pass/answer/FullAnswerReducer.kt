package com.testeducation.screen.tests.pass.answer

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.pass.answer.FullAnswerState

class FullAnswerReducer : IReducer<FullAnswerModelState, FullAnswerState> {

    override fun reduce(modelState: FullAnswerModelState): FullAnswerState {
        return FullAnswerState(modelState.answerText)
    }
}
