package com.testeducation.converter.test.question

import com.testeducation.converter.test.answer.toSimpleUIModels
import com.testeducation.converter.test.answer.toUI
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.model.test.AnswerUI

fun AnsweredQuestion.toUI(
    isExpanded: Boolean,
    isTrueAnswerExpanded: Boolean
) = when (this) {
    is AnsweredQuestion.Choose -> toUI()
    is AnsweredQuestion.Match -> toUI(isExpanded, isTrueAnswerExpanded)
    is AnsweredQuestion.Order -> toUI()
    is AnsweredQuestion.Text -> toUI()
}

fun List<AnsweredQuestion>.toUIModels(
    isExpanded: Boolean,
    isTrueAnswerExpanded: Boolean
) = map { question ->
    question.toUI(isExpanded, isTrueAnswerExpanded)
}

private fun AnsweredQuestion.Match.toUI(
    isExpanded: Boolean,
    isTrueAnswerExpanded: Boolean
) = AnsweredQuestionUI.Match(
    id,
    title,
    isCorrect,
    numberQuestion,
    matchValues,
    matchAnswers.toSimpleUIModels() as? List<AnswerUI.MatchAnswer> ?: emptyList(),
    correctAnswers.toSimpleUIModels() as List<AnswerUI.MatchAnswer>,
    isExpanded,
    isTrueAnswerExpanded
)

private fun AnsweredQuestion.Order.toUI() = AnsweredQuestionUI.Order(
    id,
    title,
    isCorrect,
    correctOrderAnswers.toSimpleUIModels() as List<AnswerUI.OrderAnswer>,
    numberQuestion,
    answeredAnswers.toSimpleUIModels() as? List<AnswerUI.OrderAnswer> ?: emptyList()
)

private fun AnsweredQuestion.Choose.toUI() = AnsweredQuestionUI.Choose(
    id,
    title,
    isCorrect,
    numberQuestion,
    chosenAnswers.map { choice ->
        choice.toUI(0, false, canSelect = true) as? AnswerUI.ChoiceAnswer
    },
    correctAnswers.map { correct ->
        correct.toUI(0, false, canSelect = true) as? AnswerUI.ChoiceAnswer
    }
)

private fun AnsweredQuestion.Text.toUI() = AnsweredQuestionUI.Text(
    id,
    title,
    isCorrect,
    numberQuestion,
    answered,
    answer.toUI(0, false, canSelect = false) as AnswerUI.TextAnswer
)
