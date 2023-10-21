package com.testeducation.core.repository.question

import com.testeducation.core.source.local.question.IQuestionLocalSource
import com.testeducation.core.source.remote.question.IQuestionRemoteSource
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.repository.question.IQuestionRepository
import com.testeducation.domain.utils.MINUTE_IN_MILLIS

class QuestionRepository(
    private val remoteSource: IQuestionRemoteSource,
    private val localSource: IQuestionLocalSource
) : IQuestionRepository {

    override suspend fun getQuestions(testId: String): List<Question> {
        val hasEntries = localSource.hasEntries(testId)
        val lastCacheTime = localSource.getLastCacheTime(testId) ?: 0
        val currentTime = System.currentTimeMillis()
        val isCacheTimeExpired = currentTime - lastCacheTime > TEST_QUESTIONS_CACHE_TIME
        if (!hasEntries || isCacheTimeExpired) {
            syncQuestions(testId)
        }
        return localSource.getQuestions(testId)
    }

    private suspend fun syncQuestions(testId: String) {
        val questions = remoteSource.getQuestions(testId)
        localSource.addQuestions(testId, questions)
    }

    private companion object {
        const val TEST_QUESTIONS_CACHE_TIME = 10 * MINUTE_IN_MILLIS
    }
}
