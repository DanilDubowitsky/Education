package com.testeducation.domain.repository.question

import com.testeducation.domain.model.question.Question

interface IQuestionRepository {

    suspend fun getQuestions(testId: String): List<Question>

}
