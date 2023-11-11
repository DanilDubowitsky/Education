package com.testeducation.core.source.local.question

import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.domain.model.question.input.InputUserAnswerData

interface IAnsweredQuestionLocalSource {

    suspend fun addAnsweredQuestions(
        testId: String,
        questions: List<InputUserAnswerData>
    )

    suspend fun getAnsweredQuestions(testId: String): List<AnsweredQuestion>

}
