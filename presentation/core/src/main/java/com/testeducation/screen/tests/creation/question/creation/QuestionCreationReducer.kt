package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.converter.test.question.toModelUi
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState

class QuestionCreationReducer :
    IReducer<QuestionCreationModelState, QuestionCreationState> {
    override fun reduce(modelState: QuestionCreationModelState): QuestionCreationState {
        return QuestionCreationState(
            answerItemUiList = modelState.answerItem.toModelUi()
        )
    }
}