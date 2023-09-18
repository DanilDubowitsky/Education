package com.testeducation.converter.test.question

import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.logic.model.question.QuestionDetailsUi

fun List<QuestionDetails>.toUi() = map {
    it.toUi()
}

fun QuestionDetails.toUi() = if (this is QuestionDetails.QuestionItemDetails) {
    QuestionDetailsUi.QuestionItemDetails(
        id = id,
        question = question.toUi()
    )
} else {
    QuestionDetailsUi.FooterAdd(
        id = id
    )
}