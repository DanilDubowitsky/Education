package com.testeducation.remote.converter.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.remote.request.question.AnswerCreateRequest

fun List<AnswerItem>.mapToRequestTypeDefault() = mapNotNull { answer ->
    if (answer is AnswerItem.DefaultAnswer) {
        AnswerCreateRequest(
            title = answer.answerText,
            current = answer.isTrue
        )
    } else null
}