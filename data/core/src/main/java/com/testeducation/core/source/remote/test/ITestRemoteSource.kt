package com.testeducation.core.source.remote.test

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.TestDetails
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort

interface ITestRemoteSource {

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
        getType: TestGetType
    ): Page<TestShort>

    suspend fun getTestDetails(id: String) : TestDetails

}