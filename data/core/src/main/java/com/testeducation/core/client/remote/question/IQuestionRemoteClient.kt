package com.testeducation.core.client.remote.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionType

interface IQuestionRemoteClient {
    suspend fun createQuestion(
        testId: String,
        type: QuestionType,
        questionText: String,
        answers: List<AnswerItem>
    )
}