package com.testeducation.logic.model.question

import com.testeducation.logic.model.test.AnswerUI

sealed interface AnsweredQuestionUI {
    val title: String
    val state: AnswerStateUI
    val id: String
    val numberQuestion: Int

    data class Choose(
        override val id: String,
        override val title: String,
        override val state: AnswerStateUI,
        override val numberQuestion: Int,
        val chosenAnswer: AnswerUI.ChoiceAnswer,
        val correctAnswer: AnswerUI.ChoiceAnswer,
    ) : AnsweredQuestionUI

    data class Order(
        override val id: String,
        override val title: String,
        override val state: AnswerStateUI,
        val correctOrderAnswers: List<AnswerUI.OrderAnswer>,
        override val numberQuestion: Int,
        val answeredAnswers: List<AnswerUI.OrderAnswer>,
    ) : AnsweredQuestionUI

    data class Text(
        override val id: String,
        override val title: String,
        override val state: AnswerStateUI,
        override val numberQuestion: Int,
        val answered: String,
    ) : AnsweredQuestionUI

    data class Match(
        override val id: String,
        override val title: String,
        override val state: AnswerStateUI,
        override val numberQuestion: Int,
        val matchValues: List<String>,
        val matchAnswers: List<AnswerUI.MatchAnswer>,
        val isExpanded: Boolean = false
    ) : AnsweredQuestionUI
}
