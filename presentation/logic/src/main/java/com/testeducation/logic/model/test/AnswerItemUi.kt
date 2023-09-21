package com.testeducation.logic.model.test

sealed class AnswerItemUi {

    abstract val id: String

    data class DefaultAnswer(
        override val id: String,
        val answerText: String,
        val isTrue: Boolean,
        val isUrl: Boolean,
        val color: Int,
        val resource: Resource = Resource()
    ) : AnswerItemUi() {
        data class Resource(
            val isTrueColor: Int = 0
        )
    }

    data class TextAnswer(override val id: String, val text: String) : AnswerItemUi()

    data class MatchAnswer(
        override val id: String,
        val firstAnswer: String,
        val secondAnswer: String,
        val color: Int
    ) : AnswerItemUi() {
        companion object {
            const val FIRST_ANSWER_MATCH = 1
            const val SECOND_ANSWER_MATCH = 2
        }
    }

    data class FooterPlusAdd(override val id: String) : AnswerItemUi()

}