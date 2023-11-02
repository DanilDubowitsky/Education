package com.testeducation.screen.tests.pass

import com.testeducation.domain.model.question.Question

data class TestPassingModelState(
    val questions: List<PassingQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedQuestionState: SelectedQuestionState = SelectedQuestionState.Choice(),
    val currentQuestion: PassingQuestion? = null
) {

    data class PassingQuestion(
        val state: AnswerState = AnswerState.NONE,
        val answers: List<String> = emptyList(),
        val timeSpent: Long = 0L,
        val question: Question
    ) {
        enum class AnswerState {
            CORRECT,
            INCORRECT,
            NONE
        }
    }

    sealed interface SelectedQuestionState {
        val question: Question?

        data class Choice(
            val selectedAnswerIndex: Int? = null,
            override val question: Question.Choice? = null
        ) : SelectedQuestionState

        data class Text(
            val answeredText: String = "",
            override val question: Question.Text? = null
        ) : SelectedQuestionState

        data class Match(
            val matchData: List<String> = emptyList(),
            override val question: Question.Match? = null
        ) : SelectedQuestionState

        data class Order(
            override val question: Question.Order? = null
        ) : SelectedQuestionState
    }
}
