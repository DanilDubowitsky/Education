package com.testeducation.domain.service.question

import com.testeducation.domain.model.question.AnswerItem

interface IQuestionService {
    suspend fun createQuestion(questionText: String, answers: List<AnswerItem>)
}