package com.testeducation.core.repository.question

import com.testeducation.core.source.local.question.IAnsweredQuestionLocalSource
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.domain.repository.question.IAnsweredQuestionRepository

class AnsweredQuestionRepository(
    private val localSource: IAnsweredQuestionLocalSource
) : IAnsweredQuestionRepository {

    override suspend fun getAnsweredQuestions(testId: String): List<AnsweredQuestion> =
        localSource.getAnsweredQuestions(testId)
}
