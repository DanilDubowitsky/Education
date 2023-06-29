package com.testeducation.core.repository.test

import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.repository.test.ITestRepository

class TestRepository(
    private val testRemoteSource: ITestRemoteSource
) : ITestRepository {

    override suspend fun getTests(
        query: String?,
        themeId: String?,
        orderField: TestOrderField?,
        orderDirection: OrderDirection?
    ): Page<TestShort> =
        testRemoteSource.getTests(
            query,
            themeId,
            orderField,
            orderDirection
        )

}
