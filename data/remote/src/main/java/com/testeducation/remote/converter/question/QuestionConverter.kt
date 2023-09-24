package com.testeducation.remote.converter.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionItem
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.remote.model.answer.RemoteAnswer
import com.testeducation.remote.model.question.RemoteQuestion
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

fun List<AnswerItem>.mapToRequestTypeWriteAnswer() = mapNotNull { answer ->
    if (answer is AnswerItem.TextAnswer) {
        AnswerCreateRequest(
            text = answer.text
        )
    } else null
}

fun List<AnswerItem>.mapToRequestAnswer(type: QuestionType) = when (type) {
    QuestionType.DEFAULT -> this.mapToRequestTypeDefault()
    QuestionType.MATCH -> this.mapToRequestTypeMatch()
    QuestionType.WRITE_ANSWER -> this.mapToRequestTypeWriteAnswer()
    else -> emptyList()
}

fun List<RemoteAnswer>.mapToModels(type: QuestionType) = map { answer ->
    when (type) {
        QuestionType.MATCH -> AnswerItem.MatchAnswer(
            id = answer.id.orEmpty(),
            firstAnswer = answer.title.orEmpty(),
            secondAnswer = answer.match?.title.orEmpty(), color = 0
        )

        QuestionType.DEFAULT -> AnswerItem.DefaultAnswer(
            id = answer.id.orEmpty(),
            answerText = answer.title.orEmpty(),
            isTrue = answer.current,
            isUrl = false, color = 0
        )

        QuestionType.WRITE_ANSWER -> AnswerItem.TextAnswer(
            id = answer.id.orEmpty(), text = answer.text.orEmpty()
        )

        QuestionType.ORDER -> AnswerItem.OrderAnswer(
            id = answer.id.orEmpty(), answerText = answer.title.orEmpty(), order = answer.order
        )
    }
}

fun List<RemoteQuestion>.toModels() = map { item ->
    val typeQuestion = QuestionType.getType(item.type)
    QuestionItem(
        id = item.id,
        title = item.title,
        type = typeQuestion,
        answers = item.answers.mapToModels(typeQuestion)
    )
}