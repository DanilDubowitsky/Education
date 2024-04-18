package com.testeducation.local.converter

import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.domain.model.result.UserAnswer
import com.testeducation.local.converter.question.toModel
import com.testeducation.local.converter.question.toQuestionWithAnswers
import com.testeducation.local.entity.result.AnsweredQuestionEntity
import com.testeducation.local.entity.result.AnsweredQuestionWithAnswers
import com.testeducation.local.entity.result.TestPassResultCompound
import com.testeducation.local.entity.result.TestPassResultEntity

fun TestPassResult.toCompound(testId: String) = TestPassResultCompound(
    passResultEntity = this.toEntity(testId),
    userAnswers = answers.toCompounds()
)

fun TestPassResultCompound.toModel() = TestPassResult(
    passResultEntity.id.orEmpty(),
    passResultEntity.testId,
    userAnswers.toModels().sortedBy {
        it.question.numberQuestion
    },
    passResultEntity.timeSpent,
    passResultEntity.wasCheating,
    passResultEntity.result.toEnumModel()
)

private fun List<AnsweredQuestionWithAnswers>.toModels() =
    this.map {
        it.toUserAnswer()
    }

private fun AnsweredQuestionWithAnswers.toUserAnswer() =
    UserAnswer(
        answeredQuestion.questionId,
        answeredQuestion.testId,
        question.toModel(),
        answeredQuestion.answeredIds,
        answeredQuestion.customAnswer,
        answeredQuestion.timeSpent,
        answeredQuestion.isCorrect
    )

private fun TestPassResult.toEntity(testId: String) = TestPassResultEntity(
    testId,
    id,
    timeSpent,
    wasCheating,
    result.toEnumEntity()
)

private fun List<UserAnswer>.toCompounds() = this.map(UserAnswer::toCompound)

private fun UserAnswer.toCompound() = AnsweredQuestionWithAnswers(
    answeredQuestion = this.toEntity(),
    question = question.toQuestionWithAnswers(this.testId)
)

private fun UserAnswer.toEntity() =
    AnsweredQuestionEntity(question.id, testId, answers, isCorrect, customAnswer, timeSpent)
