package com.testeducation.domain.service.question

import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer

interface IQuestionService {
    suspend fun createQuestion(testId: String, type: QuestionType, questionText: String, answers: List<InputAnswer>, time: Long, orderQuestion: Int)
}