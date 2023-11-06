package com.testeducation.domain.model.question

import com.testeducation.domain.model.answer.Answer

sealed interface Question {
    val id: String
    val title: String
    val numberQuestion: Int
    val time: Long

    data class Choice(
        override val id: String,
        override val title: String,
        override val numberQuestion: Int,
        override val time: Long,
        val answers: List<Answer.ChoiceAnswer>,
    ) : Question

    data class Text(
        override val id: String,
        override val title: String,
        override val numberQuestion: Int,
        override val time: Long,
        val answer: Answer.TextAnswer
    ) : Question

    data class Match(
        override val id: String,
        override val title: String,
        override val numberQuestion: Int,
        override val time: Long,
        val answers: List<Answer.MatchAnswer>
    ) : Question

    data class Order(
        override val id: String,
        override val title: String,
        override val numberQuestion: Int,
        override val time: Long,
        val answers: List<Answer.OrderAnswer>
    ) : Question
}


