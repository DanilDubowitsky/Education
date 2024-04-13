package com.testeducation.domain.repository.question

import com.testeducation.domain.model.result.TestPassResult

interface ITestResultRepository {

    suspend fun getTestResult(
        testId: String,
        isOwner: Boolean
    ): TestPassResult
}
