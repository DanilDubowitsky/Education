package com.testeducation.core.service.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.domain.service.test.ITestService

class TestService(
    private val testRemoteClient: ITestRemoteClient
) : ITestService {


    override suspend fun toggleTestLike(id: String, liked: Boolean) =
        testRemoteClient.toggleTestLike(id, liked)

}
