package com.testeducation.domain.cases.test

import com.testeducation.domain.repository.test.ITestRepository

class DeleteTest(
    private val testsRepository: ITestRepository
) {
    suspend operator fun invoke(id: String) = testsRepository.deleteTest(id)
}
