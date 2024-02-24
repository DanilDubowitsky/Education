package com.testeducation.converter.test.question

import com.testeducation.converter.test.answer.toSimpleUIModels
import com.testeducation.converter.test.answer.toUI
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.model.test.AnswerUI

fun AnsweredQuestion.toUI(
    isExpanded: Boolean
) = when (this) {
    is AnsweredQuestion.Choose -> toUI()
    is AnsweredQuestion.Match -> toUI(isExpanded)
    is AnsweredQuestion.Order -> toUI()
    is AnsweredQuestion.Text -> toUI()
}

fun List<AnsweredQuestion>.toUIModels(
    isExpanded: Boolean
) = map { question ->
    question.toUI(isExpanded)
}

private fun AnsweredQuestion.Match.toUI(isExpanded: Boolean) = AnsweredQuestionUI.Match(
    id,
    title,
    state.toUI(),
    numberQuestion,
    matchValues,
    matchAnswers.toSimpleUIModels() as List<AnswerUI.MatchAnswer>,
    isExpanded
)

private fun AnsweredQuestion.Order.toUI() = AnsweredQuestionUI.Order(
    id,
    title,
    state.toUI(),
    correctOrderAnswers.toSimpleUIModels() as List<AnswerUI.OrderAnswer>,
    numberQuestion,
    answeredAnswers.toSimpleUIModels() as List<AnswerUI.OrderAnswer>
)

private fun AnsweredQuestion.Choose.toUI() = AnsweredQuestionUI.Choose(
    id,
    title,
    state.toUI(),
    numberQuestion,
    chosenAnswers.map { choice ->
        choice.toUI(0, false) as? AnswerUI.ChoiceAnswer
    },
    correctAnswers.map { correct ->
        correct.toUI(0, false) as? AnswerUI.ChoiceAnswer
    }
)

private fun AnsweredQuestion.Text.toUI() = AnsweredQuestionUI.Text(
    id,
    title,
    state.toUI(),
    numberQuestion,
    answered
)
