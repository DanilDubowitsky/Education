package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.domain.model.question.AnswerIndicatorItem
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.QuestionTypeItem

data class QuestionCreationModelState(
    val answerItem: List<InputAnswer> = emptyList(),
    val questionTypeItem: QuestionTypeItem,
    val questionText: String = "",
    val selectedDropElement: InputAnswer? = null,
    val answerIndicatorItems: List<AnswerIndicatorItem> = emptyList(),
    val time: Long = 0
)