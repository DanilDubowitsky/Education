package com.testeducation.domain.model.question

data class PassingQuestion(
    val state: AnswerState = AnswerState.NONE,
    val answers: List<String> = emptyList(),
    val timeSpent: Long = 0L,
    val customAnswer: String,
    val question: Question,
    val matchData: List<String>
) {

    enum class AnswerState {
        CORRECT,
        INCORRECT,
        NONE
    }
}
