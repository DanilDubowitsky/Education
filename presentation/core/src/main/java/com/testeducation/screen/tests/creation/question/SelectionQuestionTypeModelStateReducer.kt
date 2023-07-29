package com.testeducation.screen.tests.creation.question

import com.testeducation.converter.test.question.toUiModel
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.logic.screen.tests.creation.question.SelectionQuestionTypeState
import com.testeducation.screen.tests.creation.TestCreationModelState

class SelectionQuestionTypeModelStateReducer :
    IReducer<SelectionQuestionTypeModelState, SelectionQuestionTypeState> {
    override fun reduce(modelState: SelectionQuestionTypeModelState): SelectionQuestionTypeState {
        return SelectionQuestionTypeState(
            questionTypeUiItems = modelState.questionTypeItems.toUiModel()
        )
    }

}