package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.converter.test.question.toListUi
import com.testeducation.converter.test.question.toModelUi
import com.testeducation.converter.test.question.toUiModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState

class QuestionCreationReducer(private val timeConverterLongToString: ITimeConverterLongToString) :
    IReducer<QuestionCreationModelState, QuestionCreationState> {
    override fun reduce(modelState: QuestionCreationModelState): QuestionCreationState {
        return QuestionCreationState(
            answerItemUiList = modelState.answerItem.toModelUi(),
            questionTypeUiItem = modelState.questionTypeItem.questionType.toUiModel(),
            visibleIndicator = modelState.questionTypeItem.questionType == QuestionType.REORDER,
            answerIndicatorItems = modelState.answerIndicatorItems.toListUi(),
            time = timeConverterLongToString.convert(modelState.time)
        )
    }
}