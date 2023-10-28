package com.testeducation.domain.cases.test

import com.testeducation.domain.repository.test.ITestRepository

class UpdateTestStyle(
    private val testsRepository: ITestRepository
) {
    suspend operator fun invoke(id: String, color: String, background: String) =
        testsRepository.updateTestStyle(id, color, background)
}