package com.testeducation.remote.source.test

import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestSettingsItem
import com.testeducation.domain.model.test.TestShort
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.converter.global.toRemote
import com.testeducation.remote.converter.test.toModel
import com.testeducation.remote.converter.test.toModels
import com.testeducation.remote.converter.test.toRemote
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
        offset: Int,
        getType: TestGetType,
        status: Test.Status,
        userId: String?
    ): Page<TestShort> {
        return when (getType) {
            TestGetType.CONTENT -> testRetrofitClient.getTests(
                query = query,
                theme = themeId,
                order = orderField?.toRemote(),
                direction = orderDirection?.toRemote(),
                minTime = minTime,
                maxTime = maxTime,
                hasLimit = hasLimit,
                minQuestions = minQuestions,
                maxQuestions = maxQuestions,
                offset = offset,
                limit = limit,
                userId = userId
            ).getResult().data.toModel()
            TestGetType.LIKED -> testRetrofitClient.getLikedTests(
                query = query,
                theme = themeId,
                order = orderField?.toRemote(),
                direction = orderDirection?.toRemote(),
                minTime = minTime,
                maxTime = maxTime,
                hasLimit = hasLimit,
                minQuestions = minQuestions,
                maxQuestions = maxQuestions,
                offset = offset,
                limit = limit
            ).getResult().data.toModel()
            TestGetType.ACCOUNT -> {
                testRetrofitClient.getAccountTests(
                    query = query,
                    theme = themeId,
                    order = orderField?.toRemote(),
                    direction = orderDirection?.toRemote(),
                    minTime = minTime,
                    maxTime = maxTime,
                    hasLimit = hasLimit,
                    minQuestions = minQuestions,
                    maxQuestions = maxQuestions,
                    offset = offset,
                    limit = limit,
                    status = status.toRemote()
                ).getResult().data.toModel()
            }
            TestGetType.PASSED -> testRetrofitClient.getPassedTests(
                query = query,
                theme = themeId,
                order = orderField?.toRemote(),
                direction = orderDirection?.toRemote(),
                minTime = minTime,
                maxTime = maxTime,
                hasLimit = hasLimit,
                minQuestions = minQuestions,
                maxQuestions = maxQuestions,
                offset = offset,
                limit = limit,
            ).getResult().data.toModel()
        }
    }

    override suspend fun getTest(id: String): Test {
        return testRetrofitClient.getTest(id = id).getResult().data.toModel()
    }

    override suspend fun getTestSettings(id: String): TestSettingsItem {
        return testRetrofitClient.getTestSettings(id = id).getResult().data.toModels()
    }

    override suspend fun updateTestSettings(id: String, testSettingsItem: TestSettingsItem) {
        testRetrofitClient.updateTestSettings(id = id, testSettingsItem.toRemote())
    }

    override suspend fun updateTestStyle(id: String, color: String, background: String) {
        testRetrofitClient.updateTestStyle(
            id = id,
            remoteTestStyle = TestStyleRequest(
                color, background
            )
        )
    }

    override suspend fun changeTest(id: String, time: Long?, status: Test.Status) {
        if (status == Test.Status.DRAFT) {
            testRetrofitClient.draft(id).getResult()
        } else {
            testRetrofitClient.publish(id, time?.div(1000)).getResult()
        }
    }

    override suspend fun getTestCode(id: String): String =
        testRetrofitClient.getTestToken(id).getResult().data.code

    override suspend fun getTestByCode(code: String): Test =
        testRetrofitClient.getTestByCode(code).getResult().data.toModel()

    override suspend fun deleteTest(testId: String) {
        testRetrofitClient.deleteTestById(testId).getResult()
    }
}
