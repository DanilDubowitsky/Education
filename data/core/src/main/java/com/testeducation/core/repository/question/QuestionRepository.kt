package com.testeducation.core.repository.question

import com.testeducation.core.source.local.question.IQuestionLocalSource
import com.testeducation.core.source.remote.question.IQuestionRemoteSource
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.repository.question.IQuestionRepository

class QuestionRepository(
    private val remoteSource: IQuestionRemoteSource,
    private val localSource: IQuestionLocalSource
) : IQuestionRepository {

    override suspend fun getQuestions(testId: String): List<Question> {
        val hasEntries = localSource.hasEntries(testId)
        if (!hasEntries) {
            syncQuestions(testId)
        }
        return localSource.getQuestions(testId)
    }

    private suspend fun syncQuestions(testId: String) {
        val questions = remoteSource.getQuestions(testId)
        localSource.deleteAndAddQuestions(testId, questions)
    }
}
