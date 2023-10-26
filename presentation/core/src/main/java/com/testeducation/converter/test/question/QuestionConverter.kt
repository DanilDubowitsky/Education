package com.testeducation.converter.test.question

import com.testeducation.converter.test.answer.toUIModels
import com.testeducation.domain.model.question.Question
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.QuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.screen.tests.pass.TestPassingModelState

fun TestPassingModelState.PassingQuestion.toUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
): QuestionUI {
    return when (val question = question) {
        is Question.Choice -> question.toChoiceUI(
            colorExtractor,
            timeConverterLongToString,
            state,
        )

        is Question.Match -> question.toMatchUI(colorExtractor, timeConverterLongToString, state)
        is Question.Order -> question.toOrderUI(colorExtractor, timeConverterLongToString, state)
        is Question.Text -> question.toTextUI(timeConverterLongToString, state)
    }
}

private fun Question.Order.toOrderUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString,
    state: TestPassingModelState.PassingQuestion.AnswerState
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
    state: TestPassingModelState.PassingQuestion.AnswerState
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
    state: TestPassingModelState.PassingQuestion.AnswerState
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
    state: TestPassingModelState.PassingQuestion.AnswerState
) = QuestionUI.Choice(
    id,
    title,
    numberQuestion,
    timeConverterLongToString.convert(time),
    state.toUI(),
    answers.toUIModels(colorExtractor, state) as List<AnswerUI.ChoiceAnswer>
)

private fun TestPassingModelState.PassingQuestion.AnswerState.toUI() = when (this) {
    TestPassingModelState.PassingQuestion.AnswerState.CORRECT -> QuestionUI.AnswerState.CORRECT
    TestPassingModelState.PassingQuestion.AnswerState.INCORRECT -> QuestionUI.AnswerState.INCORRECT
    TestPassingModelState.PassingQuestion.AnswerState.NONE -> QuestionUI.AnswerState.NONE
}

