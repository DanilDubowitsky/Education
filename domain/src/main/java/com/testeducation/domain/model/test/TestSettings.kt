package com.testeducation.domain.model.test

data class TestSettings(
    val availability: Availability,
    val previewQuestions: Boolean,
    val minCorrectAnswers: Int,
    val antiCheating: Boolean,
    val timeLimit: Int,
    val questionsOrder: QuestionsOrder
) {

    enum class QuestionsOrder {
        SEQUENTIAL,
        SHUFFLED
    }

    enum class Availability {
        PUBLIC,
        PRIVATE
    }
}