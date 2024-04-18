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
    result.toModel()
)

private fun RemotePassResult.Result.toModel() = when (this) {
    RemotePassResult.Result.Successful -> TestPassResult.ResultStatus.SUCCESSFUL
    RemotePassResult.Result.FailedMinQuestions -> TestPassResult.ResultStatus.FAILED_MIN_QUESTIONS
    RemotePassResult.Result.FailedCheating -> TestPassResult.ResultStatus.FAILED_CHEATING
    RemotePassResult.Result.Failed -> TestPassResult.ResultStatus.FAILED
}

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
