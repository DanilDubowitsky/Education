package com.testeducation.remote.converter.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.Question
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
    QuestionType.CHOICE -> this.mapToRequestTypeDefault()
    QuestionType.MATCH -> this.mapToRequestTypeMatch()
    QuestionType.TEXT -> this.mapToRequestTypeWriteAnswer()
    else -> emptyList()
}

fun List<RemoteAnswer>.mapToModels(type: QuestionType) = map { answer ->
    when (type) {
        QuestionType.MATCH -> AnswerItem.MatchAnswer(
            id = answer.id.orEmpty(),
            firstAnswer = answer.title.orEmpty(),
            secondAnswer = answer.match?.title.orEmpty(), color = 0
        )

        QuestionType.CHOICE -> AnswerItem.DefaultAnswer(
            id = answer.id.orEmpty(),
            answerText = answer.title.orEmpty(),
            isTrue = answer.current,
            isUrl = false, color = 0
        )

        QuestionType.TEXT -> AnswerItem.TextAnswer(
            id = answer.id.orEmpty(), text = answer.text.orEmpty()
        )

        QuestionType.REORDER -> AnswerItem.OrderAnswer(
            id = answer.id.orEmpty(), answerText = answer.title.orEmpty(), order = answer.order
        )
    }
}

fun RemoteQuestion.toModel(): Question {
    val questionType = type.toModel()

    return Question(
        id = id,
        title = title,
        numberQuestion = questionNumber,
        time = time.toLong(),
        type = questionType,
        answers = answers.mapToModels(questionType)
    )
}

fun List<RemoteQuestion>.toModels() = map(RemoteQuestion::toModel)

fun QuestionType.toRemote() = when (this) {
    QuestionType.MATCH -> RemoteQuestion.Type.Match
    QuestionType.CHOICE -> RemoteQuestion.Type.Choice
    QuestionType.TEXT -> RemoteQuestion.Type.Text
    QuestionType.REORDER -> RemoteQuestion.Type.Reorder
}

private fun RemoteQuestion.Type.toModel() = when (this) {
    RemoteQuestion.Type.Match -> QuestionType.MATCH
    RemoteQuestion.Type.Choice -> QuestionType.CHOICE
    RemoteQuestion.Type.Text -> QuestionType.TEXT
    RemoteQuestion.Type.Reorder -> QuestionType.REORDER
}
