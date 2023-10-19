package com.testeducation.converter.test.question

import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.input.InputQuestion
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.InputQuestionUI
import com.testeducation.logic.model.question.QuestionUI
import com.testeducation.logic.model.test.AnswerCreationUI

fun List<InputAnswer>.toModelUi() = this.map { answerItem ->
    when (answerItem) {
        is InputAnswer.DefaultAnswer -> {
            AnswerCreationUI.DefaultAnswer(
                id = answerItem.id,
                answerText = answerItem.answerText,
                isTrue = answerItem.isTrue,
                isUrl = answerItem.isUrl,
                color = answerItem.color,
                resource = AnswerCreationUI.DefaultAnswer.Resource(answerItem.resource.isTrueColor)
            )
        }

        is InputAnswer.TextAnswer -> {
            AnswerCreationUI.TextAnswer(
                id = answerItem.id,
                text = answerItem.text
            )
        }

        is InputAnswer.MatchAnswer -> {
            AnswerCreationUI.MatchAnswer(
                id = answerItem.id,
                firstAnswer = answerItem.firstAnswer,
                secondAnswer = answerItem.secondAnswer,
                color = answerItem.color
            )
        }

        is InputAnswer.OrderAnswer -> {
            AnswerCreationUI.OrderAnswer(
                id = answerItem.id,
                answerText = answerItem.answerText,
                order = answerItem.order,
                color = answerItem.color
            )
        }

        is InputAnswer.FooterPlusAdd -> {
            AnswerCreationUI.FooterPlusAdd(
                id = answerItem.id,
                isOrderAnswer = answerItem.isOrderAnswer
            )
        }
    }
}

fun List<InputQuestion>.toUi(timeConverterLongToString: ITimeConverterLongToString) = map { question ->
    question.toUi(timeConverterLongToString)
}

fun InputQuestion.toUi(timeConverterLongToString: ITimeConverterLongToString) = InputQuestionUI(
    id = id,
    title = title,
    icon = icon,
    questionTypeUiItem = type.toUiModel(),
    numberQuestion = numberQuestion,
    answerItemUiList = answers.toModelUi(),
    time = timeConverterLongToString.convert(time)
)

fun List<Question>.toUIModels(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = map { question ->
    question.toUI(answerColorExtractor, timeConverterLongToString)
}

fun Question.toUI(
    answerColorExtractor: IAnswerColorExtractor,
    timeConverterLongToString: ITimeConverterLongToString
) = QuestionUI(
    id,
    title,
    icon,
    numberQuestion,
    answers.toUIModels(answerColorExtractor),
    timeConverterLongToString.convert(time)
)
