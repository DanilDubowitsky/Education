package com.testeducation.remote.client.remote.question

import com.testeducation.core.client.remote.question.IQuestionRemoteClient
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.remote.client.retrofit.question.QuestionRetrofitClient
import com.testeducation.remote.converter.question.mapToRequestAnswer
import com.testeducation.remote.converter.question.toModel
import com.testeducation.remote.converter.question.toRemote
import com.testeducation.remote.request.question.QuestionCreateRequest
import com.testeducation.remote.utils.getResult

class QuestionRemoteClient(
    private val questionRetrofitClient: QuestionRetrofitClient
) : IQuestionRemoteClient {
    override suspend fun createQuestion(
        testId: String,
        type: QuestionType,
        questionText: String,
        answers: List<InputAnswer>,
        time: Long,
        orderQuestion: Int
    ) {
        val request = QuestionCreateRequest(
            title = questionText,
            type = type.toRemote().name,
            time = time,
            answers = answers.mapToRequestAnswer(type = type)
        )
        questionRetrofitClient.createQuestion(
            id = testId,
            request = request
        ).getResult()
    }

    override suspend fun deleteQuestion(testId: String, questionId: String) {
        questionRetrofitClient.deleteQuestion(testId = testId, questionId = questionId).getResult()
    }

    override suspend fun getQuestion(testId: String, questionId: String): Question {
        return questionRetrofitClient.getQuestions(testId = testId, questionId = questionId)
            .getResult().data.toModel()
    }

    override suspend fun updateQuestion(
        questionId: String,
        testId: String,
        type: QuestionType,
        questionText: String,
        answers: List<InputAnswer>,
        time: Long,
        orderQuestion: Int
    ) {
        val request = QuestionCreateRequest(
            title = questionText,
            type = type.toRemote().name,
            time = time,
            answers = answers.mapToRequestAnswer(type = type)
        )
        questionRetrofitClient.updateQuestion(
            id = testId,
            questionId = questionId,
            request = request
        ).getResult()
    }
}