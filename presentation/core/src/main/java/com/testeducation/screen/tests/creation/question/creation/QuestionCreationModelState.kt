package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.domain.model.question.AnswerIndicatorItem
import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionTypeItem

data class QuestionCreationModelState(
    val answerItem: List<AnswerItem> = emptyList(),
    val questionTypeItem: QuestionTypeItem,
    val questionText: String = "",
    val selectedDropElement: AnswerItem? = null,
    val answerIndicatorItems: List<AnswerIndicatorItem> = emptyList()
)