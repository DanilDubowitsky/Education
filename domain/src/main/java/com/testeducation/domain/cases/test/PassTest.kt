package com.testeducation.domain.cases.test

import com.testeducation.domain.model.question.TestPassResultType
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.domain.service.test.ITestService

class PassTest(
    private val testService: ITestService
) {

    suspend operator fun invoke(
        testId: String,
        answers: List<InputUserAnswerData>,
        spentTime: Long,
        isCheating: Boolean,
        result: TestPassResultType,
        sendToStatistic: Boolean
    ) = testService.passTest(
        testId,
        answers,
        spentTime,
        isCheating,
        result,
        sendToStatistic
    )
}