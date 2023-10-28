package com.testeducation.core.client.remote.question

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
}