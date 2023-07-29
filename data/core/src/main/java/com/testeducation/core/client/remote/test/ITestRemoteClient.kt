package com.testeducation.core.client.remote.test

import com.testeducation.domain.model.test.TestCreationShort

interface ITestRemoteClient {

    suspend fun toggleTestLike(id: String, liked: Boolean)

    suspend fun createTest(
        title: String,
        themeId: String,
        color: String,
        background: String
    ): TestCreationShort
}