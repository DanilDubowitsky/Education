package com.testeducation.domain.service.question

import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.QuestionType

interface IQuestionService {
    suspend fun createQuestion(testId: String, type: QuestionType, questionText: String, answers: List<InputAnswer>, time: Long)
}