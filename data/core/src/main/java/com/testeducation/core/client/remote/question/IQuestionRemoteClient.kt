package com.testeducation.core.client.remote.question

import com.testeducation.domain.model.question.AnswerItem

interface IQuestionRemoteClient {
    suspend fun createQuestion(
        testId: String,
        type: String,
        questionText: String,
        answers: List<AnswerItem>
    )
}