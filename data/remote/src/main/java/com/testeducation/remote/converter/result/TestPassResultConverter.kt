package com.testeducation.remote.converter.result

import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.domain.model.result.UserAnswer
import com.testeducation.remote.converter.question.toModel
import com.testeducation.remote.model.result.RemotePassResult
import com.testeducation.remote.model.result.RemoteUserAnswer

fun RemotePassResult.toModel() = TestPassResult(
    id,
    testId,
    answers.toModels(testId),
    timeSpent,
    wasCheating,
    result == RemotePassResult.Result.Successful
)

private fun RemoteUserAnswer.toModel(testId: String) =
    UserAnswer(
        id,
        testId,
        question.toModel(),
        answers,
        customAnswer,
        timeSpent,
        isCorrect
    )

private fun List<RemoteUserAnswer>.toModels(testId: String) = this.map { answer ->
    answer.toModel(testId)
}
