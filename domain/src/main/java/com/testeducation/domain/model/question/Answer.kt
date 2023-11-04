package com.testeducation.domain.model.question

import com.testeducation.domain.model.question.input.InputAnswer

sealed interface Answer {
    val id: String
    val questionId: String

    data class ChoiceAnswer(
        override val id: String,
        override val questionId: String,
        val title: String,
        val isTrue: Boolean
    ) : Answer

    data class TextAnswer(
        override val id: String,
        override val questionId: String
    ) : Answer

    data class MatchAnswer(
        override val id: String,
        override val questionId: String,
        val matchedCorrectText: String,
        val title: String
    ) : Answer

    data class OrderAnswer(
        override val id: String,
        override val questionId: String,
        val title: String,
        val order: Int
    ) : Answer

}

fun List<Answer>.convertToDomain() = map { itemAnswer ->
    when (itemAnswer) {
        is Answer.ChoiceAnswer -> {
            InputAnswer.DefaultAnswer(
                id = itemAnswer.id,
                answerText = itemAnswer.title,
                isTrue = itemAnswer.isTrue
            )
        }

        is Answer.MatchAnswer -> {
            InputAnswer.MatchAnswer(
                id = itemAnswer.id,
                firstAnswer = itemAnswer.title,
                secondAnswer = itemAnswer.matchedCorrectText
            )
        }

        is Answer.OrderAnswer -> {
            InputAnswer.OrderAnswer(
                id = itemAnswer.id,
                answerText = itemAnswer.title,
                order = itemAnswer.order
            )
        }

        is Answer.TextAnswer -> {
            InputAnswer.TextAnswer(
                id = itemAnswer.id,
                text = itemAnswer.questionId
            )
        }
    }
}