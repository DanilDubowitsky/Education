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
    val isAnswered =
        state != TestPassingModelState.PassingQuestion.AnswerState.NONE
    return when (val question = question) {
        is Question.Choice -> question.toChoiceUI(
            colorExtractor,
            timeConverterLongToString,
            isAnswered
        )

        is Question.Match -> question.toMatchUI(colorExtractor, timeConverterLongToString)
        is Question.Order -> question.toOrderUI(colorExtractor, timeConverterLongToString)
        is Question.Text -> question.toTextUI(timeConverterLongToString)
    }
}

fun Question.Order.toOrderUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = QuestionUI.Order(
    id,
    title,
    numberQuestion,
    timeConverterLongToString.convert(time),
    answers.toUIModels(colorExtractor, false) as List<AnswerUI.OrderAnswer>
)

fun Question.Match.toMatchUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = QuestionUI.Match(
    id,
    title,
    numberQuestion,
    timeConverterLongToString.convert(time),
    answers.toUIModels(colorExtractor, false) as List<AnswerUI.MatchAnswer>
)

fun Question.Text.toTextUI(
    timeConverterLongToString: ITimeConverterLongToString
) = QuestionUI.Text(
    id,
    title,
    numberQuestion,
    timeConverterLongToString.convert(time)
)

fun Question.Choice.toChoiceUI(
    colorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString,
    isAnswered: Boolean
) = QuestionUI.Choice(
    id,
    title,
    numberQuestion,
    timeConverterLongToString.convert(time),
    answers.toUIModels(colorExtractor, isAnswered) as List<AnswerUI.ChoiceAnswer>
)

