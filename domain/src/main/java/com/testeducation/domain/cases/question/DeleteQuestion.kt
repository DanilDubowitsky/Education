package com.testeducation.domain.cases.question

import com.testeducation.domain.service.question.IQuestionService

class DeleteQuestion(
    private val questionService: IQuestionService
) {
    suspend operator fun invoke(
        testId: String,
        questionId: String,
    ) = questionService.deleteQuestion(
        testId, questionId
    )
}