package com.testeducation.domain.model.question.input

sealed class InputAnswer {
    abstract val id: String

    data class DefaultAnswer(
        override val id: String,
        val answerText: String = "",
        val isTrue: Boolean = false,
        val isUrl: Boolean = false,
        val color: Int = 0,
        val resource: Resource = Resource()
    ) : InputAnswer() {
        data class Resource(
            /* Обозначает цвет при различных состояния параметра isTrue */
            val isTrueColor: Int = 0
        )
    }

    data class TextAnswer(override val id: String, val text: String = "") : InputAnswer()

    data class MatchAnswer(
        override val id: String,
        val firstAnswer: String = "",
        val secondAnswer: String = "",
        val color: Int = 0
    ) : InputAnswer()

    data class OrderAnswer(
        override val id: String,
        val answerText: String = "",
        val order: Int,
        val color: Int = 0
    ) : InputAnswer()
}