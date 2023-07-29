package com.testeducation.remote.source.test

import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.converter.global.toRemote
import com.testeducation.remote.converter.test.toModel
import com.testeducation.remote.converter.test.toModels
import com.testeducation.remote.converter.test.toRemote
import com.testeducation.remote.request.test.TestCreationRequest
import com.testeducation.remote.request.test.TestStyleRequest
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
        pageIndex: Int
    ): Page<TestShort> {
        return testRetrofitClient.getTests(
            query,
            themeId,
            orderField?.toRemote(),
            orderDirection?.toRemote(),
            minTime,
            maxTime,
            hasLimit,
            minQuestions,
            maxQuestions,
            pageIndex,
            limit
        ).getResult().data.toModel()
    }

    override suspend fun getLikedTests(): List<TestShort> {
        return testRetrofitClient.getLikedTests().getResult().data.toModels()
    }

    override suspend fun createTest(
        title: String,
        themeId: String,
        color: String,
        background: String
    ): TestCreationShort {
        val testCreationRequest = TestCreationRequest(
            title = title, themeId = themeId, style = TestStyleRequest(
                color = color, background = background
            )
        )
        return testRetrofitClient.createTest(testCreationRequest).getResult().data.toModel()
    }
}
