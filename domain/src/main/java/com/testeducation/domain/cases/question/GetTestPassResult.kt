package com.testeducation.domain.cases.question

import com.testeducation.domain.model.converter.toAnsweredQuestions
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.domain.model.result.UserAnswer
import com.testeducation.domain.repository.question.ITestResultRepository

class GetTestPassResult(
    private val repository: ITestResultRepository
) {

    suspend operator fun invoke(testId: String, isOwner: Boolean = false): PassResultWithAnsweredQuestions {
        val passResult: TestPassResult = repository.getTestResult(testId, isOwner)

        return PassResultWithAnsweredQuestions(
            passResult.id,
            passResult.testId,
            passResult.answers.toAnsweredQuestions(),
            passResult.timeSpent,
            passResult.wasCheating,
            passResult.result,
            passResult.answers.count(UserAnswer::isCorrect),
            passResult.answers.count { answer ->
                !answer.isCorrect
            }
        )
    }

    data class PassResultWithAnsweredQuestions(
        val id: String,
        val testId: String,
        val answers: List<AnsweredQuestion>,
        val timeSpent: Long,
        val wasCheating: Boolean,
        val result: TestPassResult.ResultStatus,
        val trueAnswers: Int,
        val falseAnswers: Int
    )

}
