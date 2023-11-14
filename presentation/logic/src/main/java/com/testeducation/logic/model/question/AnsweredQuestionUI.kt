package com.testeducation.logic.model.question

import com.testeducation.logic.model.test.AnswerUI

sealed interface AnsweredQuestionUI {
    val title: String
    val state: AnswerStateUI

    data class Choose(
        override val title: String,
        override val state: AnswerStateUI,
        val chosenAnswer: AnswerUI.ChoiceAnswer,
        val correctAnswer: AnswerUI.ChoiceAnswer,
    ) : AnsweredQuestionUI

    data class Order(
        override val title: String,
        override val state: AnswerStateUI,
        val correctOrderAnswers: List<AnswerUI.OrderAnswer>,
        val answeredAnswers: List<AnswerUI.OrderAnswer>
    ) : AnsweredQuestionUI

    data class Text(
        override val title: String,
        override val state: AnswerStateUI,
        val answered: String
    ) : AnsweredQuestionUI

    data class Match(
        override val title: String,
        override val state: AnswerStateUI,
        val matchValues: List<String>,
        val matchAnswers: List<AnswerUI.MatchAnswer>
    ) : AnsweredQuestionUI
}
