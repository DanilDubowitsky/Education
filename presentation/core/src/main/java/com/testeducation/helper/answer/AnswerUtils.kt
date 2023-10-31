package com.testeducation.helper.answer

import com.testeducation.domain.model.question.Question
import com.testeducation.screen.tests.pass.TestPassingModelState

fun Question.toPassingQuestion(
    state: TestPassingModelState.PassingQuestion.AnswerState =
        TestPassingModelState.PassingQuestion.AnswerState.NONE,
    spentTime: Long = 0L,
    answers: List<String> = emptyList()
) = TestPassingModelState.PassingQuestion(
    question = this,
    state = state,
    answers = answers,
    timeSpent = spentTime
)

fun List<Question>.toPassingQuestions() = map { question ->
    question.toPassingQuestion()
}
