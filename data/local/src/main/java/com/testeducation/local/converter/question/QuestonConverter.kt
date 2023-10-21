package com.testeducation.local.converter.question

import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.local.converter.answer.toEntities
import com.testeducation.local.converter.answer.toModels
import com.testeducation.local.converter.toEnumEntity
import com.testeducation.local.converter.toEnumModel
import com.testeducation.local.entity.answer.AnswerEntity
import com.testeducation.local.entity.question.QuestionEntity
import com.testeducation.local.entity.question.QuestionWithAnswers

fun List<QuestionWithAnswers>.toModels() = map(QuestionWithAnswers::toModel)

fun QuestionWithAnswers.toModel() =
    question.toModel(answers)

fun QuestionEntity.toModel(answers: List<AnswerEntity>): Question {
    val enumType = type.toEnumModel<QuestionType>()
    return Question(
        id,
        title,
        numberQuestion,
        time,
        enumType,
        answers.toModels(enumType)
    )
}

fun Question.toEntity(testId: String) = QuestionEntity(
    id,
    title,
    numberQuestion,
    time,
    type.toEnumEntity(),
    testId
)

fun List<Question>.toEntities(testId: String) = map { question ->
    question.toEntity(testId)
}

fun List<Question>.toQuestionsWithAnswers(testId: String) = map { question ->
    question.toQuestionWithAnswers(testId)
}

fun Question.toQuestionWithAnswers(testId: String) = QuestionWithAnswers(
    this.toEntity(testId),
    answers.toEntities(testId)
)
