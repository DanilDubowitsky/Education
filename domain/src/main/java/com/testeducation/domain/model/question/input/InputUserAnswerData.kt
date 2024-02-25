package com.testeducation.domain.model.question.input

data class InputUserAnswerData(
    val questionId: String,
    val answerIds: List<String>,
    val isCorrect: Boolean?,
    val spentTime: Long,
    val customAnswer: String?,
    val matchData: List<String>
)
