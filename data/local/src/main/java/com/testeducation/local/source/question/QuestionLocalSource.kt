package com.testeducation.local.source.question

import com.testeducation.core.source.local.question.IQuestionLocalSource
import com.testeducation.domain.model.question.Question

class QuestionLocalSource : IQuestionLocalSource {

    override suspend fun getQuestions(testId: String): List<Question> = emptyList()

    override suspend fun deleteAndAddQuestions(testId: String, questions: List<Question>) {

    }

    override suspend fun hasEntries(testId: String): Boolean = false

    override suspend fun deleteQuestions(testId: String) {

    }
}
