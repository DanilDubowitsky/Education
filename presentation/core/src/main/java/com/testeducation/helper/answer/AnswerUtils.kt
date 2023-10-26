package com.testeducation.helper.answer

import com.testeducation.domain.model.question.Question
import com.testeducation.screen.tests.pass.TestPassingModelState

fun Question.toPassingQuestion() = TestPassingModelState.PassingQuestion(
    question = this
)

fun List<Question>.toPassingQuestions() = map { question ->
    question.toPassingQuestion()
}
