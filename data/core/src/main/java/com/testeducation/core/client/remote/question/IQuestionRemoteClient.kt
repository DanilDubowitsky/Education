package com.testeducation.core.client.remote.question

import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.QuestionType

interface IQuestionRemoteClient {
    suspend fun createQuestion(
        testId: String,
        type: QuestionType,
        questionText: String,
        answers: List<InputAnswer>,
        time: Long
    )
}