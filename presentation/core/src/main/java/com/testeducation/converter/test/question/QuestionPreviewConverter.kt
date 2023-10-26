package com.testeducation.converter.test.question

import com.testeducation.converter.test.answer.toUIModels
import com.testeducation.domain.model.question.Question
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.QuestionPreviewUI
import com.testeducation.screen.tests.pass.TestPassingModelState

fun List<Question>.toPreviewUIs(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = map { question ->
    question.toPreviewUI(answerColorExtractor, timeConverterLongToString)
}

fun Question.toPreviewUI(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
): QuestionPreviewUI {
    return when (this) {
        is Question.Choice -> toUI(answerColorExtractor, timeConverterLongToString)
        is Question.Match -> toUI(answerColorExtractor, timeConverterLongToString)
        is Question.Order -> toUI(answerColorExtractor, timeConverterLongToString)
        is Question.Text -> toUI(answerColorExtractor, timeConverterLongToString)
    }
}

private fun Question.Choice.toUI(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = QuestionPreviewUI(
    id,
    title,
    numberQuestion,
    answers.toUIModels(answerColorExtractor, TestPassingModelState.PassingQuestion.AnswerState.NONE),
    timeConverterLongToString.convert(time)
)

private fun Question.Match.toUI(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = QuestionPreviewUI(
    id,
    title,
    numberQuestion,
    answers.toUIModels(answerColorExtractor, TestPassingModelState.PassingQuestion.AnswerState.NONE),
    timeConverterLongToString.convert(time)
)

private fun Question.Order.toUI(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = QuestionPreviewUI(
    id,
    title,
    numberQuestion,
    answers.toUIModels(answerColorExtractor, TestPassingModelState.PassingQuestion.AnswerState.NONE),
    timeConverterLongToString.convert(time)
)

private fun Question.Text.toUI(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = QuestionPreviewUI(
    id,
    title,
    numberQuestion,
    emptyList(),
    timeConverterLongToString.convert(time)
)
