package com.testeducation.core.repository.test

import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestSettingsItem
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.repository.test.ITestRepository

class TestRepository(
    private val testRemoteSource: ITestRemoteSource
) : ITestRepository {

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
        testStatus: Test.Status,
        userId: String?
    ): Page<TestShort> =
        testRemoteSource.getTests(
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
            getType,
            testStatus,
            userId
        )

    override suspend fun getTest(id: String): Test {
        return testRemoteSource.getTest(id)
    }

    override suspend fun getTestSettings(id: String): TestSettingsItem =
        testRemoteSource.getTestSettings(id)

    override suspend fun updateTestSettings(id: String, testSettings: TestSettingsItem) =
        testRemoteSource.updateTestSettings(id, testSettings)

    override suspend fun updateTestStyle(id: String, color: String, background: String) =
        testRemoteSource.updateTestStyle(
            id = id, color = color, background = background
        )

    override suspend fun changeStatusTest(id: String, time: Long?, status: Test.Status) {
        testRemoteSource.changeTest(id, time, status)
    }

    override suspend fun getTestCode(id: String): String = testRemoteSource.getTestCode(id)

    override suspend fun getTestByCode(code: String): Test = testRemoteSource.getTestByCode(code)

    override suspend fun deleteTest(testId: String) = testRemoteSource.deleteTest(testId)
}
