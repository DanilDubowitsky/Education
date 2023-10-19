package com.testeducation.domain.model.question.input

import com.testeducation.domain.model.question.QuestionType

data class InputQuestion(
    val id: String,
    val title: String,
    val numberQuestion: String = "",
    val time: Long,
    val icon: Int = 0,
    val type: QuestionType,
    val answers: List<InputAnswer>
)