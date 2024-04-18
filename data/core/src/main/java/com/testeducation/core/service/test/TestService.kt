package com.testeducation.core.service.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.core.source.local.question.ITestPassResultLocalSource
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.TestPassResultType
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.domain.model.result.UserAnswer
import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.domain.repository.question.IQuestionRepository
import com.testeducation.domain.service.test.ITestService

class TestService(
    private val testRemoteClient: ITestRemoteClient,
    private val questionRepository: IQuestionRepository,
    private val testPassResultLocalSource: ITestPassResultLocalSource
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
        result: TestPassResultType,
        sendToStatistic: Boolean
    ) {
        val questions = questionRepository.getQuestions(testId).associateBy(Question::id)
        val testPassResult = result.toPassResultStatus()
        val passResult =
            TestPassResult(
                "",
                testId,
                answers.toUserAnswers(testId, questions),
                spentTime,
                isCheating,
                testPassResult
            )
        testPassResultLocalSource.addTestPassResult(testId, passResult)
        if (sendToStatistic) {
            testRemoteClient.passTest(
                testId,
                answers,
                spentTime,
                isCheating,
                testPassResult
            )
        }
    }

    private fun List<InputUserAnswerData>.toUserAnswers(
        testId: String,
        questions: Map<String, Question>
    ) = map { answer ->
        answer.toUserAnswer(testId, questions)
    }

    private fun TestPassResultType.toPassResultStatus() = when (this) {
        TestPassResultType.SUCCESSFUL -> TestPassResult.ResultStatus.SUCCESSFUL
        TestPassResultType.FAILED -> TestPassResult.ResultStatus.FAILED
        TestPassResultType.FAILED_MIN_QUESTION -> TestPassResult.ResultStatus.FAILED_MIN_QUESTIONS
        TestPassResultType.TIME_OVER -> TestPassResult.ResultStatus.SUCCESSFUL
        TestPassResultType.ANTI_CHEAT_FAILED -> TestPassResult.ResultStatus.FAILED_CHEATING
    }

    private fun InputUserAnswerData.toUserAnswer(
        testId: String,
        questions: Map<String, Question>
    ) = UserAnswer(
        "",
        testId,
        questions.getValue(questionId),
        answerIds,
        customAnswer,
        spentTime,
        isCorrect ?: false
    )
}
