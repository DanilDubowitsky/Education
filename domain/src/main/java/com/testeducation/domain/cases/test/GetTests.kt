package com.testeducation.domain.cases.test

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.repository.test.ITestRepository

class GetTests(
    private val testRepository: ITestRepository
) {

    suspend operator fun invoke(
        query: String? = null,
        themeId: String? = null,
        orderField: TestOrderField? = null,
        orderDirection: OrderDirection = OrderDirection.ASCENDING,
        minTime: Int?,
        maxTime: Int?,
        hasLimit: Boolean,
        minQuestions: Int?,
        maxQuestions: Int?,
        limit: Int,
        pageIndex: Int
    ): Page<TestShort> = testRepository.getTests(
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
        pageIndex
    )
}
