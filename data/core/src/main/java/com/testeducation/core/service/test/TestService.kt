package com.testeducation.core.service.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.core.source.local.question.IAnsweredQuestionLocalSource
import com.testeducation.domain.model.question.TestPassResult
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.domain.service.test.ITestService

class TestService(
    private val testRemoteClient: ITestRemoteClient,
    private val answeredQuestionLocalSource: IAnsweredQuestionLocalSource
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

    override suspend fun passTest(
        testId: String,
        answers: List<InputUserAnswerData>,
        spentTime: Long,
        isCheating: Boolean,
        result: TestPassResult
    ) {
        testRemoteClient.passTest(
            testId,
            answers,
            spentTime,
            isCheating,
            result
        )
        answeredQuestionLocalSource.addAnsweredQuestions(testId, answers)
    }
}
