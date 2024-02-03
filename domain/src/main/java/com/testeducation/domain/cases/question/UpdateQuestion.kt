package com.testeducation.domain.cases.question

import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.service.question.IQuestionService

class UpdateQuestion(
    private val questionService: IQuestionService
) {
    suspend operator fun invoke(
        questionId: String,
        testId: String,
        type: QuestionType,
        questionText: String,
        answerItem: List<InputAnswer>,
        time: Long,
        orderQuestion: Int
    ) = questionService.updateQuestion(
        questionId ,testId, type, questionText, answerItem, time, orderQuestion
    )
}