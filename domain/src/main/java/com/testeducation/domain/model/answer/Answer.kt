package com.testeducation.domain.model.answer

sealed interface Answer {
    val id: String
    val questionId: String

    data class ChoiceAnswer(
        override val id: String,
        override val questionId: String,
        val title: String,
        val isTrue: Boolean
    ) : Answer

    data class TextAnswer(
        override val id: String,
        override val questionId: String
    ) : Answer

    data class MatchAnswer(
        override val id: String,
        override val questionId: String,
        val title: String,
        val matchedCorrectText: String
    ) : Answer

    data class OrderAnswer(
        override val id: String,
        override val questionId: String,
        val title: String,
        val order: Int
    ) : Answer
}