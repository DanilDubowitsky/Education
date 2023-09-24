package com.testeducation.logic.screen.tests.creation.question.creation

import com.testeducation.logic.model.test.AnswerIndicatorItemUi
import com.testeducation.logic.model.test.AnswerItemUi
import com.testeducation.logic.model.test.QuestionTypeUiItem

data class QuestionCreationState(
    val answerItemUiList: List<AnswerItemUi>,
    val questionTypeUiItem: QuestionTypeUiItem,
    val visibleIndicator: Boolean,
    val answerIndicatorItems: List<AnswerIndicatorItemUi>
)