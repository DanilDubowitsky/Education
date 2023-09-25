package com.testeducation.logic.model.question

sealed class QuestionDetailsUi {
    abstract val id: String

    data class QuestionItemDetails(
        override val id: String,
        val question: QuestionItemUi,
    ) : QuestionDetailsUi()

    data class FooterAdd(override val id: String = "-1"): QuestionDetailsUi()
}
