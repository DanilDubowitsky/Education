package com.testeducation.logic.model.test

sealed interface AnswerUI {
    val id: String

    data class ChoiceAnswer(
        override val id: String,
        val title: String,
        val isTrue: Boolean,
        val color: Int,
        val isSelected: Boolean
    ) : AnswerUI

    data class TextAnswer(
        override val id: String,
        val color: Int
    ) : AnswerUI

    data class MatchAnswer(
        override val id: String,
        val title: String,
        val matchedCorrectText: String,
        val color: Int
    ) : AnswerUI

    data class OrderAnswer(
        override val id: String,
        val title: String,
        val order: Int,
        val color: Int
    ) : AnswerUI
}