package com.testeducation.core.service.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.domain.service.test.ITestService

class TestService(
    private val testRemoteClient: ITestRemoteClient
) : ITestService {


    override suspend fun toggleTestLike(id: String, liked: Boolean) =
        testRemoteClient.toggleTestLike(id, liked)

    override suspend fun createTest(
        title: String,
        themeId: String,
        color: String,
        background: String
    ): TestCreationShort = testRemoteClient.createTest(
        title,
        themeId,
        color,
        background
    )

}
