package com.testeducation.screen.tests.pass

import com.testeducation.domain.model.answer.Answer
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
        data class Choice(
            val selectedAnswerIndex: Int? = null,
            val answers: List<Answer.ChoiceAnswer> = emptyList()
        ) : SelectedQuestionState

        class Text() : SelectedQuestionState

        class Match : SelectedQuestionState

        class Order : SelectedQuestionState
    }
}
