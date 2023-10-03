package com.testeducation.domain.cases.test

import com.testeducation.domain.model.test.Test
import com.testeducation.domain.repository.test.ITestRepository

class GetTest(
    private val testsRepository: ITestRepository
) {
    suspend operator fun invoke(id: String): Test = testsRepository.getTest(id)
}
