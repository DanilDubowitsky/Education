package com.example.remote.source.test

import com.example.core.source.remote.test.ITestRemoteSource
import com.example.domain.model.global.OrderDirection
import com.example.domain.model.test.TestOrderField
import com.example.domain.model.test.TestShort
import com.example.remote.client.retrofit.test.TestRetrofitClient
import com.example.remote.converter.global.toRemote
import com.example.remote.converter.test.toModels
import com.example.remote.converter.test.toRemote
import com.example.remote.utils.ResponseUtils.getResult

class TestRemoteSource(
    private val testRetrofitClient: TestRetrofitClient
) : ITestRemoteSource {

    override suspend fun getTests(
        query: String?,
        themeId: String?,
        orderField: TestOrderField?,
        orderDirection: OrderDirection?
    ): List<TestShort> {
        return testRetrofitClient.getTests(
            query,
            themeId,
            orderField?.toRemote(),
            orderDirection?.toRemote()
        ).getResult().data.toModels()
    }
}
