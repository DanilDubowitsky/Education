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

fun List<Answer>.convertToDomain(getColor: ((Int) -> Int)? = null, getTrueColor: ((Boolean) -> Int)? = null) =
    mapIndexed { index, itemAnswer ->
        when (itemAnswer) {
            is Answer.ChoiceAnswer -> {
                InputAnswer.DefaultAnswer(
                    id = itemAnswer.id,
                    answerText = itemAnswer.title,
                    isTrue = itemAnswer.isTrue,
                    color = getColor?.invoke(index) ?: 0,
                    resource = InputAnswer.DefaultAnswer.Resource(
                        isTrueColor = getTrueColor?.invoke(itemAnswer.isTrue) ?: 0
                    )
                )
            }

            is Answer.MatchAnswer -> {
                InputAnswer.MatchAnswer(
                    id = itemAnswer.id,
                    firstAnswer = itemAnswer.title,
                    secondAnswer = itemAnswer.matchedCorrectText,
                    color = getColor?.invoke(index) ?: 0
                )
            }

            is Answer.OrderAnswer -> {
                InputAnswer.OrderAnswer(
                    id = itemAnswer.id,
                    answerText = itemAnswer.title,
                    order = itemAnswer.order,
                    color = getColor?.invoke(index) ?: 0
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