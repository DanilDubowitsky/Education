package com.testeducation.local.converter.answer

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.local.entity.answer.AnswerEntity

fun List<AnswerEntity>.toModels(questionType: QuestionType) = map { answer ->
    answer.toModel(questionType)
}

fun AnswerEntity.toModel(questionType: QuestionType) =
    when (questionType) {
        QuestionType.MATCH -> toMatch()
        QuestionType.CHOICE -> toChoice()
        QuestionType.TEXT -> toText()
        QuestionType.REORDER -> toOrder()
    }

fun List<Answer>.toEntities() = map { answer ->
    answer.toEntity()
}

fun Answer.toEntity() = when (this) {
    is Answer.ChoiceAnswer -> toChoiceEntity()
    is Answer.MatchAnswer -> toMatchEntity()
    is Answer.OrderAnswer -> toOrderEntity()
    is Answer.TextAnswer -> toTextEntity()
}

private fun Answer.TextAnswer.toTextEntity() =
    AnswerEntity(id, questionId, "", correctText = correctText)

private fun Answer.ChoiceAnswer.toChoiceEntity() = AnswerEntity(
    id,
    questionId,
    title,
    isTrue = isTrue
)

private fun Answer.MatchAnswer.toMatchEntity() = AnswerEntity(
    id,
    questionId,
    title,
    matchedCorrectText = matchedCorrectText
)

private fun Answer.OrderAnswer.toOrderEntity() = AnswerEntity(
    id,
    questionId,
    title,
    order = order
)

private fun AnswerEntity.toChoice() = Answer.ChoiceAnswer(
    id,
    questionId,
    title,
    isTrue!!
)

private fun AnswerEntity.toText() = Answer.TextAnswer(
    id,
    questionId,
    correctText!!
)

private fun AnswerEntity.toMatch() = Answer.MatchAnswer(
    id,
    questionId,
    title,
    matchedCorrectText!!
)

private fun AnswerEntity.toOrder() = Answer.OrderAnswer(
    id,
    questionId,
    title,
    order!!
)
