package com.testeducation.domain.repository.test

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestSettingsItem
import com.testeducation.domain.model.test.TestShort

interface ITestRepository {

    suspend fun getTests(
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
    ): Page<TestShort>

    suspend fun getTest(id: String): Test

    suspend fun getTestSettings(id: String): TestSettingsItem

    suspend fun updateTestSettings(id: String, testSettings: TestSettingsItem)

    suspend fun updateTestStyle(id: String, color: String, background: String)

    suspend fun changeStatusTest(id: String, status: Test.Status)

    suspend fun getTestCode(id: String): String

    suspend fun getTestByCode(code: String): Test

    suspend fun deleteTest(testId: String)
}
