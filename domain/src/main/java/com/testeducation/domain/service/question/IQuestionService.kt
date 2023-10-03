package com.testeducation.domain.service.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionType

interface IQuestionService {
    suspend fun createQuestion(testId: String, type: QuestionType, questionText: String, answers: List<AnswerItem>, time: Long)
}