package com.testeducation.domain.service.test

import com.testeducation.domain.model.test.TestCreationShort

interface ITestService {

    suspend fun toggleTestLike(id: String, liked: Boolean)

    suspend fun createTest(
        title: String,
        themeId: String,
        color: String,
        background: String
    ): TestCreationShort

}
