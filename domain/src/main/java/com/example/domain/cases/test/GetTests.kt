package com.example.domain.cases.test

import com.example.domain.model.global.OrderDirection
import com.example.domain.model.test.TestOrderField
import com.example.domain.model.test.TestShort
import com.example.domain.repository.test.ITestRepository

class GetTests(
    private val testRepository: ITestRepository
) {

    suspend operator fun invoke(
        query: String? = null,
        themeId: String? = null,
        orderField: TestOrderField? = null,
        orderDirection: OrderDirection = OrderDirection.ASCENDING
    ): List<TestShort> = testRepository.getTests(
        query,
        themeId,
        orderField,
        orderDirection
    )
}
