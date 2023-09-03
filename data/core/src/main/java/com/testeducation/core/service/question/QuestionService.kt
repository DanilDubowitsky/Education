package com.testeducation.core.service.question

import com.testeducation.core.client.remote.question.IQuestionRemoteClient
import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.service.question.IQuestionService

class QuestionService(
    private val questionRemoteClient: IQuestionRemoteClient
) : IQuestionService {
    override suspend fun createQuestion(
        testId: String,
        type: String,
        questionText: String,
        answers: List<AnswerItem>
    ) {
        questionRemoteClient.createQuestion(testId, type, questionText, answers)
    }
}