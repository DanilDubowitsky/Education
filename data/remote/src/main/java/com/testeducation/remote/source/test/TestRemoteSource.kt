package com.testeducation.remote.source.test

import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.model.test.TestShort
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.converter.test.toModels
import com.testeducation.remote.utils.getResult

class TestRemoteSource(
    private val testRetrofitClient: TestRetrofitClient
) : ITestRemoteSource {

    override suspend fun getTests(): List<TestShort> =
        testRetrofitClient.getTests().getResult().data.toModels()

}
