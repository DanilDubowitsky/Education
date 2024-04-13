package com.testeducation.remote.converter.question

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.TestPassResultType
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.remote.model.answer.AnswerMatch
import com.testeducation.remote.model.answer.InputUserAnswerDataRemote
import com.testeducation.remote.model.answer.RemoteAnswer
import com.testeducation.remote.model.question.RemoteQuestion
import com.testeducation.remote.model.test.TestPassResultRemote
import com.testeducation.remote.request.question.answer.AnswerCreateRequest

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

fun List<InputAnswer>.mapToRequestTypeOrder() = mapNotNull { answer ->
    if (answer is InputAnswer.OrderAnswer) {
        AnswerCreateRequest(
            title = answer.answerText,
            order = answer.order
        )
    } else null
}

fun List<InputAnswer>.mapToRequestTypeWriteAnswer() = mapNotNull { answer ->
    if (answer is InputAnswer.TextAnswer) {
        AnswerCreateRequest(
            text = answer.text,
            title = answer.text
        )
    } else null
}

fun List<InputAnswer>.mapToRequestAnswer(type: QuestionType) = when (type) {
    QuestionType.CHOICE -> this.mapToRequestTypeDefault()
    QuestionType.MATCH -> this.mapToRequestTypeMatch()
    QuestionType.TEXT -> this.mapToRequestTypeWriteAnswer()
    QuestionType.REORDER -> this.mapToRequestTypeOrder()
}

fun RemoteQuestion.toModel(): Question {
    val questionType = type.toModel()

    return when (type) {
        RemoteQuestion.Type.Match -> Question.Match(
            id,
            title,
            questionNumber.toInt(),
            time.toLong(),
            answers.toModels(questionType,id) as List<Answer.MatchAnswer>
        )

        RemoteQuestion.Type.Choice -> Question.Choice(
            id,
            title,
            questionNumber.toInt(),
            time.toLong(),
            answers.toModels(questionType, id) as List<Answer.ChoiceAnswer>
        )

        RemoteQuestion.Type.Text -> Question.Text(
            id,
            title,
            questionNumber.toInt(),
            time.toLong(),
            answers.toModels(questionType, id) as List<Answer.TextAnswer>
        )

        RemoteQuestion.Type.Reorder -> Question.Order(
            id,
            title,
            questionNumber.toInt(),
            time.toLong(),
            answers.toModels(questionType, id) as List<Answer.OrderAnswer>
        )
    }
}

fun List<RemoteAnswer>.toModels(
    questionType: QuestionType,
    questionId: String
) = map { answer ->
    answer.toModel(questionId, questionType)
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

fun InputUserAnswerData.toRemote() = InputUserAnswerDataRemote(
    questionId,
    answerIds,
    isCorrect,
    spentTime,
    customAnswer
)

fun List<InputUserAnswerData>.toRemotes() = map(InputUserAnswerData::toRemote)

fun TestPassResultType.toRemote() = when (this) {
    TestPassResultType.SUCCESSFUL -> TestPassResultRemote.Successful
    TestPassResultType.FAILED,
    TestPassResultType.TIME_OVER,
    TestPassResultType.ANTI_CHEAT_FAILED -> TestPassResultRemote.Failed
}

private fun RemoteAnswer.toMatch(questionId: String) = Answer.MatchAnswer(
    id,
    questionId,
    title.orEmpty(),
    match?.title.orEmpty()
)

private fun RemoteAnswer.toChoice(questionId: String) = Answer.ChoiceAnswer(
    id,
    questionId,
    title!!,
    current!!
)

private fun RemoteAnswer.toText(questionId: String) = Answer.TextAnswer(
    id,
    questionId,
    text.orEmpty()
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
