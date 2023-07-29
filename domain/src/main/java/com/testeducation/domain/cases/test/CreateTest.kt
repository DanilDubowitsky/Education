package com.testeducation.domain.cases.test

import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.domain.service.test.ITestService

class CreateTest (
    private val testService: ITestService
) {

    suspend operator fun invoke(
        title: String,
        themeId: String,
        color: String,
        background: String
    ): TestCreationShort = testService.createTest(
        title,
        themeId,
        color,
        background
    )
}
