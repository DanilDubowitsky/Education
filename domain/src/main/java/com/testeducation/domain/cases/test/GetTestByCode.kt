package com.testeducation.domain.cases.test

import com.testeducation.domain.repository.test.ITestRepository

class GetTestByCode(
    private val testRepository: ITestRepository
) {

    suspend operator fun invoke(code: String) = testRepository.getTestByCode(code)
}