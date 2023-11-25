package com.testeducation.domain.repository.question

import com.testeducation.domain.model.question.answered.AnsweredQuestion

interface IAnsweredQuestionRepository {

    suspend fun getAnsweredQuestions(testId: String): List<AnsweredQuestion>
}
