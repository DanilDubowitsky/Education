package com.testeducation.logic.model.question

import com.testeducation.logic.model.test.AnswerUI

sealed interface QuestionUI {
    val id: String
    val title: String
    val numberQuestion: Int
    val time: String
    val answerState: AnswerState

    data class Choice(
        override val id: String,
        override val title: String,
        override val numberQuestion: Int,
        override val time: String,
        override val answerState: AnswerState,
        val answers: List<AnswerUI.ChoiceAnswer>,
    ) : QuestionUI

    data class Text(
        override val id: String,
        override val title: String,
        override val numberQuestion: Int,
        override val answerState: AnswerState,
        override val time: String,
    ) : QuestionUI

    data class Match(
        override val id: String,
        override val title: String,
        override val numberQuestion: Int,
        override val time: String,
        override val answerState: AnswerState,
        val answers: List<AnswerUI.MatchAnswer>,
    ) : QuestionUI

    data class Order(
        override val id: String,
        override val title: String,
        override val numberQuestion: Int,
        override val time: String,
        override val answerState: AnswerState,
        val answers: List<AnswerUI.OrderAnswer>,
    ) : QuestionUI

    enum class AnswerState {
        CORRECT,
        INCORRECT,
        NONE
    }
}
