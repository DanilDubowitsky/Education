package com.testeducation.domain.service.question

import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer

interface IQuestionService {
    suspend fun createQuestion(testId: String, type: QuestionType, questionText: String, answers: List<InputAnswer>, time: Long, orderQuestion: Int)
    suspend fun deleteQuestion(testId: String, questionId: String)
    suspend fun getQuestionDetails(testId: String, questionId: String): Question
}