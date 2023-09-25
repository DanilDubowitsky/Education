package com.testeducation.domain.model.question

data class QuestionItem(
    val id: String,
    val title: String,
    val numberQuestion: String = "",
    val icon: Int = 0,
    val type: QuestionType,
    val answers: List<AnswerItem>
)