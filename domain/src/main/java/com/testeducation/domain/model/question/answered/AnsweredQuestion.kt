package com.testeducation.domain.model.question.answered

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.PassingQuestion

sealed interface AnsweredQuestion {
    val id: String
    val title: String
    val state: PassingQuestion.AnswerState
    val numberQuestion: Int

    data class Choose(
        override val id: String,
        override val title: String,
        override val state: PassingQuestion.AnswerState,
        override val numberQuestion: Int,
        val chosenAnswer: Answer.ChoiceAnswer,
        val correctAnswer: Answer.ChoiceAnswer,
    ) : AnsweredQuestion

    data class Order(
        override val id: String,
        override val title: String,
        override val state: PassingQuestion.AnswerState,
        override val numberQuestion: Int,
        val correctOrderAnswers: List<Answer.OrderAnswer>,
        val answeredAnswers: List<Answer.OrderAnswer>,
    ) : AnsweredQuestion

    data class Text(
        override val id: String,
        override val title: String,
        override val state: PassingQuestion.AnswerState,
        override val numberQuestion: Int,
        val answered: String,
    ) : AnsweredQuestion

    data class Match(
        override val id: String,
        override val title: String,
        override val state: PassingQuestion.AnswerState,
        override val numberQuestion: Int,
        val matchValues: List<String>,
        val matchAnswers: List<Answer.MatchAnswer>,
    ) : AnsweredQuestion

}