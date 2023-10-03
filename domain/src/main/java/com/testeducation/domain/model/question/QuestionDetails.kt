package com.testeducation.domain.model.question

sealed class QuestionDetails {
    abstract val id: String

    data class QuestionItemDetails(
        override val id: String,
        val question: Question,
    ) : QuestionDetails()

    data class FooterAdd(override val id: String = "-1"): QuestionDetails()
}