package com.testeducation.remote.client.remote.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.utils.getResult

class TestRemoteClient(
    private val testRetrofitClient: TestRetrofitClient
) : ITestRemoteClient {

    override suspend fun likeTest(id: String) =
        testRetrofitClient.likeTest(id).getResult()

}
