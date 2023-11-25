package com.testeducation.local.source.question

import com.testeducation.core.source.local.question.IQuestionLocalSource
import com.testeducation.domain.model.question.Question
import com.testeducation.local.converter.question.toModels
import com.testeducation.local.converter.question.toQuestionsWithAnswers
import com.testeducation.local.dao.question.QuestionDao
import com.testeducation.local.dao.question.TestLocalLiveTimeDao
import com.testeducation.local.entity.question.TestLocalLiveTimeEntity

class QuestionLocalSource(
    private val questionDao: QuestionDao,
    private val testLocalLiveTimeDao: TestLocalLiveTimeDao
) : IQuestionLocalSource {

    override suspend fun getQuestions(testId: String): List<Question> =
        questionDao.getQuestions(testId).toModels()

    override suspend fun addQuestions(testId: String, questions: List<Question>) {
        testLocalLiveTimeDao.insertOrUpdate(
            TestLocalLiveTimeEntity(
                testId, System.currentTimeMillis()
            )
        )
        questionDao.removeAndAddQuestions(testId, questions.toQuestionsWithAnswers(testId))
    }

    override suspend fun hasEntries(testId: String): Boolean = questionDao.hasEntries(testId)

    override suspend fun deleteQuestions(testId: String) = questionDao.removeQuestions(testId)

    override suspend fun getLastCacheTime(testId: String): Long? =
        testLocalLiveTimeDao.getTestLiveTime(testId)
}
