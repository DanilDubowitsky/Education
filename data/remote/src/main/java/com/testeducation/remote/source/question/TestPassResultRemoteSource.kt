package com.testeducation.remote.source.question

import com.testeducation.core.source.remote.question.ITestPassResultRemoteSource
import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.converter.result.toModel
import com.testeducation.remote.utils.getResult

class TestPassResultRemoteSource(
    private val questionRetrofitClient: TestRetrofitClient
) : ITestPassResultRemoteSource {

    override suspend fun getTestPassResult(testId: String): TestPassResult {
        return questionRetrofitClient.getTestPassResult(testId).getResult().data.toModel()
    }

}
