package com.testeducation.core.repository.question

import com.testeducation.core.source.local.question.ITestPassResultLocalSource
import com.testeducation.core.source.remote.question.ITestPassResultRemoteSource
import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.domain.repository.question.ITestResultRepository

class TestResultRepository(
    private val remoteSource: ITestPassResultRemoteSource,
    private val localSource: ITestPassResultLocalSource
) : ITestResultRepository {

    override suspend fun getTestResult(testId: String, isOwner: Boolean): TestPassResult {
        if (!isOwner) {
            val result = remoteSource.getTestPassResult(testId)
            localSource.addTestPassResult(testId, result)
        }
        return localSource.getPassResult(testId)
    }
}
