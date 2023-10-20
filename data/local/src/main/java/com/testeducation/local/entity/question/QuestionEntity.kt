package com.testeducation.local.entity.question

import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.QuestionType

data class QuestionEntity(
    val id: String,
    val title: String,
    val numberQuestion: String = "",
    val time: Long,
    val icon: Int = 0,
    val type: QuestionType,
    val answers: List<InputAnswer>,
    val testId: String
)
