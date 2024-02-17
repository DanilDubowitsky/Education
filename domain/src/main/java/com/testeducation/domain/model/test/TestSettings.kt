package com.testeducation.domain.model.test

data class TestSettings(
    val availability: Availability,
    val previewQuestions: Boolean,
    val minCorrectAnswers: Int,
    val antiCheating: Boolean,
    val timeLimit: Int
) {

    enum class Availability {
        PUBLIC,
        ViaLinkAll
    }
}