package com.testeducation.domain.model.question

data class QuestionItem(
    val id: String,
    val title: String,
    val type: QuestionType,
    val answers: List<AnswerItem>
)