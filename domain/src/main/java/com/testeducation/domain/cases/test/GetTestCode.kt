package com.testeducation.domain.cases.test

import com.testeducation.domain.repository.test.ITestRepository

class GetTestCode(
    private val testRepository: ITestRepository
) {

    suspend operator fun invoke(id: String) = testRepository.getTestCode(id)

}