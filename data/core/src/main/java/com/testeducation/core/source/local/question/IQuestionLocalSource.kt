package com.testeducation.core.source.local.question

import com.testeducation.domain.model.question.Question

interface IQuestionLocalSource {

    suspend fun getQuestions(testId: String): List<Question>

    suspend fun addQuestions(testId: String, questions: List<Question>)

    suspend fun hasEntries(testId: String): Boolean

    suspend fun deleteQuestions(testId: String)

    suspend fun getLastCacheTime(testId: String): Long?

    suspend fun deleteAndAddQuestions(testId: String, questions: List<Question>)
}
