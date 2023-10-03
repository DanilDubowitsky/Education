package com.testeducation.domain.cases.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.service.question.IQuestionService

class QuestionCreate(
    private val questionService: IQuestionService
) {
    suspend operator fun invoke(
        testId: String,
        type: QuestionType,
        questionText: String,
        answerItem: List<AnswerItem>,
        time: Long
    ) = questionService.createQuestion(
        testId, type, questionText, answerItem, time
    )
}