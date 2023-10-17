package com.testeducation.core.repository.question

import com.testeducation.core.source.remote.question.IQuestionRemoteSource
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.repository.question.IQuestionRepository

class QuestionRepository(
    private val questionRemoteSource: IQuestionRemoteSource
) : IQuestionRepository {

    override suspend fun getQuestions(testId: String): List<Question> =
        questionRemoteSource.getQuestions(testId)
}
