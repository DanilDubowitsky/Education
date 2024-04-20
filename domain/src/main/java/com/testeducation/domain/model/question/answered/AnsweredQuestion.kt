package com.testeducation.domain.model.question.answered

import com.testeducation.domain.model.answer.Answer

sealed interface AnsweredQuestion {
    val id: String
    val title: String
    val isCorrect: Boolean
    val numberQuestion: Int

    data class Choose(
        override val id: String,
        override val title: String,
        override val isCorrect: Boolean,
        override val numberQuestion: Int,
        val chosenAnswers: List<Answer.ChoiceAnswer>,
        val correctAnswers: List<Answer.ChoiceAnswer>,
    ) : AnsweredQuestion

    data class Order(
        override val id: String,
        override val title: String,
        override val isCorrect: Boolean,
        override val numberQuestion: Int,
        val correctOrderAnswers: List<Answer.OrderAnswer>,
        val answeredAnswers: List<Answer.OrderAnswer?>,
    ) : AnsweredQuestion

    data class Text(
        override val id: String,
        override val title: String,
        override val isCorrect: Boolean,
        override val numberQuestion: Int,
        val answered: String,
        val answer: Answer.TextAnswer
    ) : AnsweredQuestion

    data class Match(
        override val id: String,
        override val title: String,
        override val isCorrect: Boolean,
        override val numberQuestion: Int,
        val matchValues: List<String>,
        val matchAnswers: List<Answer.MatchAnswer?>,
        val correctAnswers: List<Answer.MatchAnswer>
    ) : AnsweredQuestion

}
