package com.testeducation.converter.test.question

import com.testeducation.converter.test.answer.toUIModels
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.answer.toChoiceAnswers
import com.testeducation.helper.answer.toMatchAnswers
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.QuestionUI

fun List<Question>.toUIModels(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = map { question ->
    question.toUI(answerColorExtractor, timeConverterLongToString)
}

fun Question.toUI(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = when (type) {
    QuestionType.MATCH ->
        QuestionType.CHOICE

    -> TODO()
    QuestionType.TEXT -> TODO()
    QuestionType.REORDER -> TODO()
}

private fun Question.toMatch(timeConverterLongToString: ITimeConverterLongToString) =
    QuestionUI.Match(
        id,
        title,
        numberQuestion.toInt(),
        timeConverterLongToString.convert(time),
        answers.toMatchAnswers()
    )
