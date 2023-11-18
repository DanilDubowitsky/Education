package com.testeducation.converter.test.question

import com.testeducation.converter.test.answer.toSimpleUIModels
import com.testeducation.converter.test.answer.toUI
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.model.test.AnswerUI

fun AnsweredQuestion.toUI() = when (this) {
    is AnsweredQuestion.Choose -> toUI()
    is AnsweredQuestion.Match -> toUI()
    is AnsweredQuestion.Order -> toUI()
    is AnsweredQuestion.Text -> toUI()
}

fun List<AnsweredQuestion>.toUIModels() = map(AnsweredQuestion::toUI)

private fun AnsweredQuestion.Match.toUI() = AnsweredQuestionUI.Match(
    id,
    title,
    state.toUI(),
    matchValues,
    matchAnswers.toSimpleUIModels() as List<AnswerUI.MatchAnswer>
)

private fun AnsweredQuestion.Order.toUI() = AnsweredQuestionUI.Order(
    id,
    title,
    state.toUI(),
    correctOrderAnswers.toSimpleUIModels() as List<AnswerUI.OrderAnswer>,
    answeredAnswers.toSimpleUIModels() as List<AnswerUI.OrderAnswer>
)

private fun AnsweredQuestion.Choose.toUI() = AnsweredQuestionUI.Choose(
    id,
    title,
    state.toUI(),
    chosenAnswer.toUI(0, null) as AnswerUI.ChoiceAnswer,
    correctAnswer.toUI(0, null) as AnswerUI.ChoiceAnswer
)

private fun AnsweredQuestion.Text.toUI() = AnsweredQuestionUI.Text(
    id,
    title,
    state.toUI(),
    answered
)
