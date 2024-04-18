package com.testeducation.core.source.remote.question

import com.testeducation.domain.model.result.TestPassResult

interface ITestPassResultRemoteSource {
    suspend fun getTestPassResult(testId: String): TestPassResult
}
