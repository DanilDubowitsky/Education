package com.testeducation.core.service.question

import com.testeducation.core.client.remote.question.IQuestionRemoteClient
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.service.question.IQuestionService

class QuestionService(
    private val questionRemoteClient: IQuestionRemoteClient
) : IQuestionService {
    override suspend fun createQuestion(
        testId: String,
        type: QuestionType,
        questionText: String,
        answers: List<InputAnswer>,
        time: Long,
        orderQuestion: Int
    ) {
        questionRemoteClient.createQuestion(testId, type, questionText, answers, time, orderQuestion)
    }
}