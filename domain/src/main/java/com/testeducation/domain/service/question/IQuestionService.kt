package com.testeducation.domain.service.question

import com.testeducation.domain.model.question.AnswerItem

interface IQuestionService {
    suspend fun createQuestion(testId: String, type: String, questionText: String, answers: List<AnswerItem>)
}