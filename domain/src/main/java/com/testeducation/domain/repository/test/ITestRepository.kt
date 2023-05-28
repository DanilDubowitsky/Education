package com.testeducation.domain.repository.test

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort

interface ITestRepository {

    suspend fun getTests(
        query: String?,
        themeId: String?,
        orderField: TestOrderField?,
        orderDirection: OrderDirection?
    ): List<TestShort>
}
