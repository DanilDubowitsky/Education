package com.testeducation.domain.cases.test

import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.repository.test.ITestRepository

class GetLikedTests(
    private val testsRepository: ITestRepository
) {

    suspend operator fun invoke(): List<TestShort> = testsRepository.getLikedTests()
}
