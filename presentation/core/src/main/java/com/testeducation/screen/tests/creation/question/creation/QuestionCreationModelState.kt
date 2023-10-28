package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.domain.model.question.AnswerIndicatorItem
import com.testeducation.domain.model.question.QuestionTypeItem
import com.testeducation.domain.model.question.input.InputAnswer

data class QuestionCreationModelState(
    val answerItem: List<InputAnswer> = emptyList(),
    val questionTypeItem: QuestionTypeItem,
    val questionText: String = "",
    val selectedDropElement: InputAnswer? = null,
    //Исключительно для Order вопроса
    val answerIndicatorItems: List<AnswerIndicatorItem> = emptyList(),
    val time: Long = 0
)