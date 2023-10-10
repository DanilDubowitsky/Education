package com.testeducation.core.source.remote.question

import com.testeducation.domain.model.question.Question

interface IQuestionRemoteSource {
    suspend fun getQuestions(testId: String): List<Question>
}