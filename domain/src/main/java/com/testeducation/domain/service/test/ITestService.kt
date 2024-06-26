package com.testeducation.domain.service.test

import com.testeducation.domain.model.question.TestPassResultType
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.domain.model.test.TestCreationShort

interface ITestService {

    suspend fun toggleTestLike(id: String, liked: Boolean)

    suspend fun createTest(
        title: String,
        themeId: String,
        color: String,
        background: String
    ): TestCreationShort

    suspend fun passTest(
        testId: String,
        answers: List<InputUserAnswerData>,
        spentTime: Long,
        isCheating: Boolean,
        result: TestPassResultType,
        sendToStatistic: Boolean
    )
}
