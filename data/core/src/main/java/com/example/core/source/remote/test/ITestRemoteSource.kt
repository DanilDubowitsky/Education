package com.example.core.source.remote.test

import com.example.domain.model.global.OrderDirection
import com.example.domain.model.test.TestOrderField
import com.example.domain.model.test.TestShort

interface ITestRemoteSource {

    suspend fun getTests(
        query: String?,
        themeId: String?,
        orderField: TestOrderField?,
        orderDirection: OrderDirection?
    ): List<TestShort>

}