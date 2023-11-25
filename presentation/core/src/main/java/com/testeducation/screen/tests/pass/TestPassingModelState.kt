package com.testeducation.screen.tests.pass

import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.test.Test

data class TestPassingModelState(
    val questions: List<PassingQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedQuestionState: SelectedQuestionState = SelectedQuestionState.Choice(),
    val currentQuestion: PassingQuestion? = null,
    val test: Test? = null,
    val resumeCount: Int = 0
) {

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
