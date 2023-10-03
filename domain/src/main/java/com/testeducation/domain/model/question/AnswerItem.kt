package com.testeducation.domain.model.question

sealed class AnswerItem {
    abstract val id: String

    data class DefaultAnswer(
        override val id: String,
        val answerText: String = "",
        val isTrue: Boolean = false,
        val isUrl: Boolean = false,
        val color: Int = 0,
        val resource: Resource = Resource()
    ) : AnswerItem() {
        data class Resource(
            /* Обозначает цвет при различных состояния параметра isTrue */
            val isTrueColor: Int = 0
        )
    }

    data class TextAnswer(override val id: String, val text: String = "") : AnswerItem()

    data class MatchAnswer(
        override val id: String,
        val firstAnswer: String = "",
        val secondAnswer: String = "",
        val color: Int = 0
    ) : AnswerItem() {
        companion object {
            const val FIRST_ANSWER_MATCH = 1
            const val SECOND_ANSWER_MATCH = 2
        }
    }

    data class OrderAnswer(
        override val id: String,
        val answerText: String = "",
        val order: Int,
        val color: Int = 0
    ) : AnswerItem()

    data class FooterPlusAdd(override val id: String = "-1", val isOrderAnswer: Boolean = false) : AnswerItem()
}