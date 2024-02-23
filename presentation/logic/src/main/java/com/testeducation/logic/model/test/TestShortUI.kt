package com.testeducation.logic.model.test

import com.testeducation.logic.model.theme.ThemeShortUI

sealed interface TestShortUI {
    data class Test(
        val id: String,
        val title: String,
        val questionsCount: Int,
        val isPublic: Boolean,
        val likes: Int,
        val passesCount: Int,
        val theme: ThemeShortUI,
        val color: String,
        val style: CardTestStyle,
        val liked: Boolean,
        val passed: Boolean
    ) : TestShortUI {

        data class Settings(
            val availability: Availability,
            val previewQuestions: Boolean,
            val minCorrectAnswers: Int,
            val antiCheating: Boolean,
            val timeLimit: Int,
            val questionsOrder: String
        ) {

            enum class Availability {
                PUBLIC,
                PRIVATE
            }
        }
    }

    object Loader : TestShortUI
}


