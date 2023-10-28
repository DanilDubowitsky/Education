package com.testeducation.remote.converter.question

import com.testeducation.domain.model.question.Answer
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.remote.model.answer.RemoteAnswer
import com.testeducation.remote.model.question.RemoteQuestion
import com.testeducation.remote.request.question.AnswerCreateRequest
import com.testeducation.remote.request.question.AnswerMatch

fun List<InputAnswer>.mapToRequestTypeDefault() = mapNotNull { answer ->
    if (answer is InputAnswer.DefaultAnswer) {
        AnswerCreateRequest(
            title = answer.answerText,
            current = answer.isTrue
        )
    } else null
}

fun List<InputAnswer>.mapToRequestTypeMatch() = mapNotNull { answer ->
    if (answer is InputAnswer.MatchAnswer) {
        AnswerCreateRequest(
            title = answer.firstAnswer,
            match = AnswerMatch(title = answer.secondAnswer)
        )
    } else null
}

fun List<InputAnswer>.mapToRequestTypeWriteAnswer() = mapNotNull { answer ->
    if (answer is InputAnswer.TextAnswer) {
        AnswerCreateRequest(
            text = answer.text
        )
    } else null
}

fun List<InputAnswer>.mapToRequestAnswer(type: QuestionType) = when (type) {
    QuestionType.CHOICE -> this.mapToRequestTypeDefault()
    QuestionType.MATCH -> this.mapToRequestTypeMatch()
    QuestionType.TEXT -> this.mapToRequestTypeWriteAnswer()
    else -> emptyList()
}

fun RemoteQuestion.toModel(): Question {
    val questionType = type.toModel()

    return Question(
        id = id,
        title = title,
        numberQuestion = questionNumber,
        time = time.toLong(),
        type = questionType,
        answers = answers.toModels(questionType)
    )
}

fun List<RemoteAnswer>.toModels(questionType: QuestionType) = map { answer ->
    answer.toModel(answer.id, questionType)
}

fun RemoteAnswer.toModel(
    questionId: String,
    questionType: QuestionType
) = when (questionType) {
    QuestionType.MATCH -> toMatch(questionId)
    QuestionType.CHOICE -> toChoice(questionId)
    QuestionType.TEXT -> toText(questionId)
    QuestionType.REORDER -> toReorder(questionId)
}

fun List<RemoteQuestion>.toModels(): List<Question> = map(RemoteQuestion::toModel)

fun QuestionType.toRemote() = when (this) {
    QuestionType.MATCH -> RemoteQuestion.Type.Match
    QuestionType.CHOICE -> RemoteQuestion.Type.Choice
    QuestionType.TEXT -> RemoteQuestion.Type.Text
    QuestionType.REORDER -> RemoteQuestion.Type.Reorder
}

private fun RemoteAnswer.toMatch(questionId: String) = Answer.MatchAnswer(
    id = id,
    questionId = questionId,
    matchedCorrectText = match!!.title,
    title = title!!
)

private fun RemoteAnswer.toChoice(questionId: String) = Answer.ChoiceAnswer(
    id,
    questionId,
    title!!,
    current!!
)

private fun RemoteAnswer.toText(questionId: String) = Answer.TextAnswer(
    id,
    questionId
)

private fun RemoteAnswer.toReorder(questionId: String) = Answer.OrderAnswer(
    id,
    questionId,
    title!!,
    order
)

private fun RemoteQuestion.Type.toModel() = when (this) {
    RemoteQuestion.Type.Match -> QuestionType.MATCH
    RemoteQuestion.Type.Choice -> QuestionType.CHOICE
    RemoteQuestion.Type.Text -> QuestionType.TEXT
    RemoteQuestion.Type.Reorder -> QuestionType.REORDER
}
