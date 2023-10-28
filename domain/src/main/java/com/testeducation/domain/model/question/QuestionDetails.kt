package com.testeducation.domain.model.question

import com.testeducation.domain.model.question.input.InputQuestion

sealed class QuestionDetails {
    abstract val id: String

    data class QuestionItemDetails(
        override val id: String,
        val question: InputQuestion,
    ) : QuestionDetails()

    data class FooterAdd(override val id: String = "-1"): QuestionDetails()
}