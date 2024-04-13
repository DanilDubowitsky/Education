package com.testeducation.remote.client.remote.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.domain.model.question.TestPassResultType
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.converter.question.toRemote
import com.testeducation.remote.converter.question.toRemotes
import com.testeducation.remote.converter.test.toModel
import com.testeducation.remote.request.test.PassTestRequest
import com.testeducation.remote.request.test.TestCreationRequest
import com.testeducation.remote.request.test.TestStyleRequest
import com.testeducation.remote.utils.getResult

class TestRemoteClient(
    private val testRetrofitClient: TestRetrofitClient
) : ITestRemoteClient {

    override suspend fun toggleTestLike(id: String, liked: Boolean) {
        return if (liked) testRetrofitClient.likeTest(id).getResult()
        else testRetrofitClient.unlikeTest(id).getResult()
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

    override suspend fun passTest(
        testId: String,
        answers: List<InputUserAnswerData>,
        spentTime: Long,
        isCheating: Boolean,
        result: TestPassResultType
    ) {
        val remoteAnswers = answers.toRemotes()
        val request = PassTestRequest(
            testId,
            remoteAnswers,
            spentTime,
            isCheating,
            result.toRemote()
        )

        return testRetrofitClient.passTest(request).getResult()
    }
}
