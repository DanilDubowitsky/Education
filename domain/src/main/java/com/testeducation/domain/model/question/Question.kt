package com.testeducation.domain.model.question

data class Question(
    val id: String,
    val title: String,
    val numberQuestion: String = "",
    val time: Long,
    val icon: Int = 0,
    val type: QuestionType,
    val answers: List<AnswerItem>
)