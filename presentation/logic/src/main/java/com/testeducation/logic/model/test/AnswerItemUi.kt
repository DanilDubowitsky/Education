package com.testeducation.logic.model.test

sealed class AnswerItemUi {

    abstract val id: Int

    data class DefaultAnswer(
        override val id: Int,
        val answerText: String,
        val isTrue: Boolean,
        val isUrl: Boolean,
        val color: Int
    ) : AnswerItemUi()

    data class TextAnswer(override val id: Int) : AnswerItemUi()

    data class MatchAnswer(override val id: Int) : AnswerItemUi()

    data class FooterPlusAdd(override val id: Int) : AnswerItemUi()

}