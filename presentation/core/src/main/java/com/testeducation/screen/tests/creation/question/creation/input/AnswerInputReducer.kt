package com.testeducation.screen.tests.creation.question.creation.input

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.creation.question.creation.input.AnswerInputState

class AnswerInputReducer :
    IReducer<AnswerInputModelState, AnswerInputState> {

    override fun reduce(modelState: AnswerInputModelState): AnswerInputState {
        return AnswerInputState(
            answerText = modelState.answerText,
            color = modelState.color,
            maxLengthText = modelState.maxLengthText
        )
    }

}