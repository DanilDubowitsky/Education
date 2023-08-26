package com.testeducation.core.client.remote.question

import com.testeducation.domain.model.question.AnswerItem

interface IQuestionRemoteClient {
    suspend fun createQuestion(questionText: String, answers: List<AnswerItem>)
}