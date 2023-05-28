package com.testeducation.domain.cases.test

import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.repository.test.ITestRepository

class GetTests(
    private val testRepository: ITestRepository
) {

    suspend operator fun invoke(): List<TestShort> = testRepository.getTests()
}
