package com.testeducation.screen.tests.creation.question.creation

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
            answerItemUiList = modelState.answerItem.toModelUi(modelState.visibleAddFooter),
            questionTypeUiItem = modelState.questionTypeItem.questionType.toUiModel(),
            visibleIndicator = modelState.questionTypeItem.questionType == QuestionType.REORDER,
            time = timeConverterLongToString.convert(modelState.time),
            questionText = modelState.questionText,
            loadingScreen = modelState.loadingScreen
        )
    }
}