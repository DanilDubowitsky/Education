package com.testeducation.domain.model.question

data class PassingQuestion(
    val state: AnswerState = AnswerState.NONE,
    val answers: List<String> = emptyList(),
    val timeSpent: Long = 0L,
    val question: Question
) {

    enum class AnswerState {
        CORRECT,
        INCORRECT,
        NONE
    }
}