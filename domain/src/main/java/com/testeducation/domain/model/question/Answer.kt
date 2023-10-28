package com.testeducation.domain.model.question

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
        val matchedCorrectText: String,
        val title: String
    ) : Answer

    data class OrderAnswer(
        override val id: String,
        override val questionId: String,
        val title: String,
        val order: Int
    ) : Answer
}