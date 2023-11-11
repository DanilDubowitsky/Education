package com.testeducation.domain.model.question.answered

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.PassingQuestion

sealed interface AnsweredQuestion {

    val title: String
    val state: PassingQuestion.AnswerState

    data class Choose(
        override val title: String,
        override val state: PassingQuestion.AnswerState,
        val chosenAnswer: Answer.ChoiceAnswer,
        val correctAnswer: Answer.ChoiceAnswer,
    ) : AnsweredQuestion

    data class Order(
        override val title: String,
        override val state: PassingQuestion.AnswerState,
        val correctOrderAnswers: List<Answer.OrderAnswer>,
        val answeredAnswers: List<Answer.OrderAnswer>
    ) : AnsweredQuestion

    data class Text(
        override val title: String,
        override val state: PassingQuestion.AnswerState,
        val answered: String
    ) : AnsweredQuestion

    data class Match(
        override val title: String,
        override val state: PassingQuestion.AnswerState,
        val matchValues: List<String>,
        val matchAnswers: List<Answer.MatchAnswer>
    ) : AnsweredQuestion

}
