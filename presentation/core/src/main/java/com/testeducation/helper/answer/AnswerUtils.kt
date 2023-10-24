package com.testeducation.helper.answer

import com.testeducation.domain.model.question.Answer
import com.testeducation.domain.model.question.Question
import com.testeducation.screen.tests.pass.TestPassingModelState

fun List<Answer>.toChoiceAnswers() = map(Answer::toChoice)

fun List<Answer>.toMatchAnswers() = map(Answer::toMatch)

fun Answer.toChoice() = this as Answer.ChoiceAnswer

fun Answer.toMatch() = this as Answer.MatchAnswer

fun List<Answer>.toOrder() = map(Answer::toOrder)

fun Answer.toOrder() = this as Answer.OrderAnswer

fun Question.toPassingQuestion() = TestPassingModelState.PassingQuestion(
    question = this
)

fun List<Question>.toPassingQuestions() = map { question ->
    question.toPassingQuestion()
}
