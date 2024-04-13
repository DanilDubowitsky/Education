package com.testeducation.domain.model.result

import com.testeducation.domain.model.question.Question

data class UserAnswer(
    val id: String,
    val testId: String,
    val question: Question,
    val answers: List<String>,
    val customAnswer: String?,
    val timeSpent: Long,
    val isCorrect: Boolean
)
