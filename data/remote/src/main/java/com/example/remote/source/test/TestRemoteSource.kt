package com.example.remote.source.test

import com.example.core.source.remote.test.ITestRemoteSource
import com.example.domain.model.test.TestShort
import com.example.remote.client.retrofit.test.TestRetrofitClient
import com.example.remote.converter.test.toModels
import com.example.remote.utils.ResponseUtils.getResult

class TestRemoteSource(
    private val testRetrofitClient: TestRetrofitClient
) : ITestRemoteSource {

    override suspend fun getTests(): List<TestShort> =
        testRetrofitClient.getTests().getResult().data.toModels()

}
