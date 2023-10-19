package com.testeducation.converter.test.question

import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.QuestionDetailsUi

fun List<QuestionDetails>.toUi(timeConverterLongToString: ITimeConverterLongToString) = map {
    it.toUi(timeConverterLongToString)
}

fun QuestionDetails.toUi(timeConverterLongToString: ITimeConverterLongToString) = if (
    this is QuestionDetails.QuestionItemDetails
) {
    QuestionDetailsUi.QuestionItemDetails(
        id = id,
        question = question.toUi(timeConverterLongToString)
    )
} else {
    QuestionDetailsUi.FooterAdd(
        id = id
    )
}