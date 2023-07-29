package com.testeducation.domain.cases.test

import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.domain.repository.test.ITestRepository

class CreateTest (
    private val testRepository: ITestRepository
) {

    suspend operator fun invoke(
        title: String,
        themeId: String,
        color: String,
        background: String
    ): TestCreationShort = testRepository.createTest(
        title = title, themeId = themeId, color = color, background = background
    )
}
