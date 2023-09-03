package com.testeducation.remote.converter.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.remote.request.question.AnswerCreateRequest
import com.testeducation.remote.request.question.AnswerMatch

fun List<AnswerItem>.mapToRequestTypeDefault() = mapNotNull { answer ->
    if (answer is AnswerItem.DefaultAnswer) {
        AnswerCreateRequest(
            title = answer.answerText,
            current = answer.isTrue
        )
    } else null
}

fun List<AnswerItem>.mapToRequestTypeMatch() = mapNotNull { answer ->
    if (answer is AnswerItem.MatchAnswer) {
        AnswerCreateRequest(
            title = answer.firstAnswer,
            match = AnswerMatch(title = answer.secondAnswer)
        )
    } else null
}

fun List<AnswerItem>.mapToRequestAnswer(type: QuestionType) = when (type) {
    QuestionType.DEFAULT -> this.mapToRequestTypeDefault()
    QuestionType.MATCH -> this.mapToRequestTypeMatch()
    else -> emptyList()
}