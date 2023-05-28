package com.example.core.repository.test

import com.example.core.source.remote.test.ITestRemoteSource
import com.example.domain.model.global.OrderDirection
import com.example.domain.model.test.TestOrderField
import com.example.domain.model.test.TestShort
import com.example.domain.repository.test.ITestRepository

class TestRepository(
    private val testRemoteSource: ITestRemoteSource
) : ITestRepository {

    override suspend fun getTests(
        query: String?,
        themeId: String?,
        orderField: TestOrderField?,
        orderDirection: OrderDirection?
    ): List<TestShort> =
        testRemoteSource.getTests(
            query,
            themeId,
            orderField,
            orderDirection
        )

}
