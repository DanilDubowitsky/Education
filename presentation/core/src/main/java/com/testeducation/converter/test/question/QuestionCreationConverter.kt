package com.testeducation.converter.test.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionItem
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.QuestionItemUi
import com.testeducation.logic.model.test.AnswerItemUi

fun List<AnswerItem>.toModelUi() = this.map { answerItem ->
    when (answerItem) {
        is AnswerItem.DefaultAnswer -> {
            AnswerItemUi.DefaultAnswer(
                id = answerItem.id,
                answerText = answerItem.answerText,
                isTrue = answerItem.isTrue,
                isUrl = answerItem.isUrl,
                color = answerItem.color,
                resource = AnswerItemUi.DefaultAnswer.Resource(answerItem.resource.isTrueColor)
            )
        }

        is AnswerItem.TextAnswer -> {
            AnswerItemUi.TextAnswer(
                id = answerItem.id,
                text = answerItem.text
            )
        }

        is AnswerItem.MatchAnswer -> {
            AnswerItemUi.MatchAnswer(
                id = answerItem.id,
                firstAnswer = answerItem.firstAnswer,
                secondAnswer = answerItem.secondAnswer,
                color = answerItem.color
            )
        }

        is AnswerItem.OrderAnswer -> {
            AnswerItemUi.OrderAnswer(
                id = answerItem.id,
                answerText = answerItem.answerText,
                order = answerItem.order,
                color = answerItem.color
            )
        }

        is AnswerItem.FooterPlusAdd -> {
            AnswerItemUi.FooterPlusAdd(
                id = answerItem.id,
                isOrderAnswer = answerItem.isOrderAnswer
            )
        }
    }
}

fun List<QuestionItem>.toUi(timeConverterLongToString: ITimeConverterLongToString) = map { question ->
    question.toUi(timeConverterLongToString)
}

fun QuestionItem.toUi(timeConverterLongToString: ITimeConverterLongToString) = QuestionItemUi(
    id = id,
    title = title,
    icon = icon,
    questionTypeUiItem = type.toUiModel(),
    numberQuestion = numberQuestion,
    answerItemUiList = answers.toModelUi(),
    time = timeConverterLongToString.convert(time)
)