package com.testeducation.domain.cases.test

import com.testeducation.domain.model.test.Test
import com.testeducation.domain.repository.test.ITestRepository

class ChangeStatusTest(
    private val testsRepository: ITestRepository,
) {
    suspend operator fun invoke(id: String, time: Long?, status: Test.Status) =
        testsRepository.changeStatusTest(id, time, status)
}
