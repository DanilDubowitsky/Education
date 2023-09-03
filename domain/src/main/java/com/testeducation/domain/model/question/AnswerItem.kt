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

    data class MatchAnswer(
        override val id: Int,
        val firstAnswer: String = "",
        val secondAnswer: String = "",
        val color: Int = 0
    ) : AnswerItem() {
        companion object {
            const val FIRST_ANSWER_MATCH = 1
            const val SECOND_ANSWER_MATCH = 2
        }
    }

    data class FooterPlusAdd(override val id: Int = -1) : AnswerItem()
}