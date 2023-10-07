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
        getType: TestGetType
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
            getType
        )

    override suspend fun getTest(id: String): Test {
        return testRemoteSource.getTest(id)
    }

    override suspend fun getTestSettings(id: String): TestSettingsItem = testRemoteSource.getTestSettings(id)
}
