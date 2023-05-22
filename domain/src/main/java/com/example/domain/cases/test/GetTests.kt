package com.example.domain.cases.test

import com.example.domain.model.test.TestShort
import com.example.domain.repository.test.ITestRepository

class GetTests(
    private val testRepository: ITestRepository
) {

    suspend operator fun invoke(): List<TestShort> = testRepository.getTests()
}
