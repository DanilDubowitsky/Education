package com.testeducation.logic.model.test

sealed class AnswerCreationUI {

    abstract val id: String

    data class DefaultAnswer(
        override val id: String,
        val answerText: String,
        val isTrue: Boolean,
        val isUrl: Boolean,
        val color: Int,
        val resource: Resource = Resource()
    ) : AnswerCreationUI() {
        data class Resource(
            val isTrueColor: Int = 0
        )
    }

    data class TextAnswer(override val id: String, val text: String) : AnswerCreationUI()

    data class OrderAnswer(
        override val id: String,
        val answerText: String,
        val order: Int,
        val color: Int
    ) : AnswerCreationUI()

    data class MatchAnswer(
        override val id: String,
        val firstAnswer: String,
        val secondAnswer: String,
        val color: Int
    ) : AnswerCreationUI() {
        companion object {
            const val FIRST_ANSWER_MATCH = 1
            const val SECOND_ANSWER_MATCH = 2
        }
    }

    data class FooterPlusAdd(
        override val id: String
    ) : AnswerCreationUI()

}