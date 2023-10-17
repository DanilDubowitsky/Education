package com.testeducation.remote.source.test

import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.converter.global.toRemote
import com.testeducation.remote.converter.test.toModel
import com.testeducation.remote.converter.test.toModels
import com.testeducation.remote.converter.test.toRemote
import com.testeducation.remote.utils.getResult

class TestRemoteSource(
    private val testRetrofitClient: TestRetrofitClient
) : ITestRemoteSource {

    override suspend fun getTests(
        query: String?,
        themeId: String?,
        orderField: TestOrderField?,
        orderDirection: OrderDirection?,
        minTime: Int?,
        maxTime: Int?,
        hasLimit: Boolean,
        minQuestions: Int?,
        maxQuestions: Int?,
        limit: Int,
        offset: Int,
        getType: TestGetType,
        userId: String?
    ): Page<TestShort> {
        val method = when (getType) {
            TestGetType.LIKED -> testRetrofitClient::getLikedTests
            TestGetType.CREATED -> testRetrofitClient::getCreatedTests
            TestGetType.PASSED -> testRetrofitClient::getPassedTests
            TestGetType.MAIN -> {
                return getMainTests(
                    query,
                    themeId,
                    orderField,
                    orderDirection,
                    minTime,
                    maxTime,
                    hasLimit,
                    minQuestions,
                    maxQuestions,
                    limit,
                    offset,
                    userId
                )
            }
        }

        return method.invoke(
            query,
            themeId,
            orderField?.toRemote(),
            orderDirection?.toRemote(),
            minTime,
            maxTime,
            hasLimit,
            minQuestions,
            maxQuestions,
            offset,
            limit
        ).getResult().data.toModel()
    }

    override suspend fun getTest(id: String): Test {
        return testRetrofitClient.getTest(id = id).getResult().data.toModels()
    }

    private suspend fun getMainTests(
        query: String?,
        themeId: String?,
        orderField: TestOrderField?,
        orderDirection: OrderDirection?,
        minTime: Int?,
        maxTime: Int?,
        hasLimit: Boolean,
        minQuestions: Int?,
        maxQuestions: Int?,
        limit: Int,
        offset: Int,
        userId: String?
    ): Page<TestShort> =
        testRetrofitClient.getTests(
            query,
            themeId,
            orderField?.toRemote(),
            orderDirection?.toRemote(),
            minTime,
            maxTime,
            hasLimit,
            minQuestions,
            maxQuestions,
            offset,
            limit,
            userId
        ).getResult().data.toModel()
}
