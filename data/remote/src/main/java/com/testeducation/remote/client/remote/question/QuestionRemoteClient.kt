package com.testeducation.remote.client.remote.question

import com.testeducation.core.client.remote.question.IQuestionRemoteClient
import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.remote.client.retrofit.question.QuestionRetrofitClient
import com.testeducation.remote.converter.question.mapToRequestAnswer
import com.testeducation.remote.converter.question.mapToRequestTypeDefault
import com.testeducation.remote.request.question.QuestionCreateRequest

class QuestionRemoteClient(
    private val questionRetrofitClient: QuestionRetrofitClient
) : IQuestionRemoteClient {
    override suspend fun createQuestion(
        testId: String,
        type: QuestionType,
        questionText: String,
        answers: List<AnswerItem>
    ) {
        val request = QuestionCreateRequest(
            title = questionText,
            type = type.typeName,
            time = 0,
            answers = answers.mapToRequestAnswer(type = type)
        )
        questionRetrofitClient.createQuestion(
            id = testId,
            request = request
        )
    }
}