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
        val passed: Boolean,
        val settings: Settings
    ) : TestShortUI {

        data class Settings(
            val availability: Availability,
            val previewQuestions: Boolean
        ) {

            enum class Availability {
                PUBLIC,
                PRIVATE
            }
        }
    }

    object Loader : TestShortUI
}


