package com.testeducation.helper.answer

import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.Question
import com.testeducation.screen.tests.pass.TestPassingModelState

fun Question.toPassingQuestion(
    state: PassingQuestion.AnswerState =
        PassingQuestion.AnswerState.NONE,
    spentTime: Long = 0L,
    answers: List<String> = emptyList()
) = PassingQuestion(
    question = this,
    state = state,
    answers = answers,
    timeSpent = spentTime,
    customAnswer = ""
)

fun List<Question>.toPassingQuestions() = map { question ->
    question.toPassingQuestion()
}
