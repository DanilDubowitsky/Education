package com.testeducation.domain.cases.test

import com.testeducation.domain.model.test.TestDetails
import com.testeducation.domain.repository.test.ITestRepository

class GetTestDetails(
    private val testsRepository: ITestRepository
) {
    suspend operator fun invoke(id: String): TestDetails = testsRepository.getTestDetails(id)
}