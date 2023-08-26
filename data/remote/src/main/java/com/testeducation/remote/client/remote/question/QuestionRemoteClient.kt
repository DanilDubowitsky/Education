package com.testeducation.remote.client.remote.question

import com.testeducation.core.client.remote.question.IQuestionRemoteClient
import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.remote.client.retrofit.question.QuestionRetrofitClient
import com.testeducation.remote.converter.question.mapToRequestTypeDefault
import com.testeducation.remote.request.question.QuestionCreateRequest

class QuestionRemoteClient(
    private val questionRetrofitClient: QuestionRetrofitClient
) : IQuestionRemoteClient {
    override suspend fun createQuestion(questionText: String, answers: List<AnswerItem>) {
        val request = QuestionCreateRequest(
            title = questionText, type = "", time = 0, answers = answers.mapToRequestTypeDefault()
        )
    }
}