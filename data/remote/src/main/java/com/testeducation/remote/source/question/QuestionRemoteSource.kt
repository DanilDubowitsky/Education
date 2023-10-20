package com.testeducation.remote.source.question

import com.testeducation.core.source.remote.question.IQuestionRemoteSource
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.input.InputQuestion
import com.testeducation.remote.client.retrofit.question.QuestionRetrofitClient
import com.testeducation.remote.converter.question.toModels
import com.testeducation.remote.utils.getResult

class QuestionRemoteSource(
    private val questionRemoteClient: QuestionRetrofitClient
) : IQuestionRemoteSource {

    override suspend fun getQuestions(testId: String): List<Question> =
        questionRemoteClient.getQuestions(testId).getResult().data.toModels()
}
