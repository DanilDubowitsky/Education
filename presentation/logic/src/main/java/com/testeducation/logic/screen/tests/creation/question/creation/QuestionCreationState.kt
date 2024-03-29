package com.testeducation.logic.screen.tests.creation.question.creation

import com.testeducation.logic.model.test.AnswerCreationUI
import com.testeducation.logic.model.test.QuestionTypeUiItem

data class QuestionCreationState(
    val answerItemUiList: List<AnswerCreationUI>,
    val questionTypeUiItem: QuestionTypeUiItem,
    val visibleIndicator: Boolean,
    val time: String,
    val questionText: String,
    val loadingScreen: Boolean
)