package com.testeducation.domain.model.question

sealed class AnswerItem {
    abstract val id: Int

    data class DefaultAnswer(
        override val id: Int,
        val answerText: String = "",
        val isTrue: Boolean = false,
        val isUrl: Boolean = false,
        val color: Int = 0
    ) : AnswerItem()

    data class TextAnswer(override val id: Int) : AnswerItem()

    data class MatchAnswer(override val id: Int) : AnswerItem()

    data class FooterPlusAdd(override val id: Int = -1) : AnswerItem()
}