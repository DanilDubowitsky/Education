package com.testeducation.remote.client.remote.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.utils.getResult

class TestRemoteClient(
    private val testRetrofitClient: TestRetrofitClient
) : ITestRemoteClient {

    override suspend fun toggleTestLike(id: String, liked: Boolean) {
        return if (liked) testRetrofitClient.unlikeTest(id).getResult()
        else testRetrofitClient.likeTest(id).getResult()
    }
}
