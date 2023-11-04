package com.testeducation.domain.cases.question

import com.testeducation.domain.service.question.IQuestionService

class GetQuestionDetails(
    private val questionService: IQuestionService
) {
    suspend operator fun invoke(
        testId: String,
        questionId: String,
    ) = questionService.getQuestionDetails(
        testId, questionId
    )
}