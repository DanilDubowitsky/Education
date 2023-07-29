package com.testeducation.remote.client.remote.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.converter.test.toModel
import com.testeducation.remote.request.test.TestCreationRequest
import com.testeducation.remote.request.test.TestStyleRequest
import com.testeducation.remote.utils.getResult

class TestRemoteClient(
    private val testRetrofitClient: TestRetrofitClient
) : ITestRemoteClient {

    override suspend fun toggleTestLike(id: String, liked: Boolean) {
        return if (liked) testRetrofitClient.unlikeTest(id).getResult()
        else testRetrofitClient.likeTest(id).getResult()
    }

    override suspend fun createTest(
        title: String,
        themeId: String,
        color: String,
        background: String
    ): TestCreationShort {
        val request = TestCreationRequest(
            title,
            themeId,
            TestStyleRequest(color, background)
        )

        return testRetrofitClient.createTest(
            request
        ).getResult().data.toModel()
    }
}
