package com.testeducation.core.client.remote.question

import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer

interface IQuestionRemoteClient {
    suspend fun createQuestion(
        testId: String,
        type: QuestionType,
        questionText: String,
        answers: List<InputAnswer>,
        time: Long,
        orderQuestion: Int
    )

    suspend fun deleteQuestion(testId: String, questionId: String)

    suspend fun getQuestion(testId: String, questionId: String): Question

    suspend fun updateQuestion(
        questionId: String,
        testId: String,
        type: QuestionType,
        questionText: String,
        answers: List<InputAnswer>,
        time: Long,
        orderQuestion: Int
    )
}