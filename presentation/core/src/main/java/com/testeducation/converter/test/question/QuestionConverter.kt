package com.testeducation.converter.test.question

import com.testeducation.converter.test.answer.toUIModels
import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.Question
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.AnswerStateUI
import com.testeducation.logic.model.question.QuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.screen.tests.pass.TestPassingModelState

fun PassingQuestion.toUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString,
    selectedAnswerId: String? = null
): QuestionUI {
    return when (val question = question) {
        is Question.Choice -> question.toChoiceUI(
            colorExtractor,
            timeConverterLongToString,
            state,
            selectedAnswerId
        )

        is Question.Match -> question.toMatchUI(colorExtractor, timeConverterLongToString, state)
        is Question.Order -> question.toOrderUI(colorExtractor, timeConverterLongToString, state)
        is Question.Text -> question.toTextUI(timeConverterLongToString, state)
    }
}

fun PassingQuestion.AnswerState.toUI() = when (this) {
    PassingQuestion.AnswerState.CORRECT -> AnswerStateUI.CORRECT
    PassingQuestion.AnswerState.INCORRECT -> AnswerStateUI.INCORRECT
    PassingQuestion.AnswerState.NONE -> AnswerStateUI.NONE
}

private fun Question.Order.toOrderUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString,
    state: PassingQuestion.AnswerState
) = QuestionUI.Order(
    id,
    title,
    numberQuestion,
    timeConverterLongToString.convert(time),
    state.toUI(),
    answers.toUIModels(colorExtractor, state) as List<AnswerUI.OrderAnswer>,
)

private fun Question.Match.toMatchUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString,
    state: PassingQuestion.AnswerState
) = QuestionUI.Match(
    id,
    title,
    numberQuestion,
    timeConverterLongToString.convert(time),
    state.toUI(),
    answers.toUIModels(colorExtractor, state) as List<AnswerUI.MatchAnswer>
)

private fun Question.Text.toTextUI(
    timeConverterLongToString: ITimeConverterLongToString,
    state: PassingQuestion.AnswerState
) = QuestionUI.Text(
    id,
    title,
    numberQuestion,
    state.toUI(),
    timeConverterLongToString.convert(time)
)

private fun Question.Choice.toChoiceUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString,
    state: PassingQuestion.AnswerState,
    selectedAnswerId: String?
) = QuestionUI.Choice(
    id,
    title,
    numberQuestion,
    timeConverterLongToString.convert(time),
    state.toUI(),
    answers.toUIModels(colorExtractor, state, selectedAnswerId) as List<AnswerUI.ChoiceAnswer>
)
