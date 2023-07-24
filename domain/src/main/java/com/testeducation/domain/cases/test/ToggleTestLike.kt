package com.testeducation.domain.cases.test

import com.testeducation.domain.service.test.ITestService

class ToggleTestLike(
    private val testService: ITestService
) {

    suspend operator fun invoke(id: String, liked: Boolean) = testService.toggleTestLike(id, liked)
}