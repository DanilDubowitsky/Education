package com.testeducation.domain.model.test

data class TestSettings(
    val availability: Availability,
    val previewQuestions: Boolean
) {

    enum class Availability {
        PUBLIC,
        PRIVATE
    }
}