package com.testeducation.domain.cases.test

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
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
        minTime: Int? = null,
        maxTime: Int? = null,
        hasLimit: Boolean = false,
        minQuestions: Int? = null,
        maxQuestions: Int? = null,
        userId: String? = null,
        limit: Int,
        offset: Int,
        getType: TestGetType,
        status: Test.Status = Test.Status.PUBLISHED
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
        offset,
        getType,
        status,
        userId
    )
}
