package com.testeducation.local.source.question

import com.testeducation.core.source.local.question.IAnsweredQuestionLocalSource
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.local.converter.question.toEntities
import com.testeducation.local.converter.question.toModel
import com.testeducation.local.dao.question.AnsweredQuestionDao
import com.testeducation.local.entity.question.AnsweredQuestionWithAnswers

class AnsweredQuestionLocalSource(
    private val answeredQuestionDao: AnsweredQuestionDao
) : IAnsweredQuestionLocalSource {

    override suspend fun addAnsweredQuestions(testId: String, questions: List<InputUserAnswerData>) =
        answeredQuestionDao.insertOrUpdate(questions.toEntities(testId))

    override suspend fun getAnsweredQuestions(testId: String): List<AnsweredQuestion> =
        answeredQuestionDao.getAnsweredQuestions(testId).map(AnsweredQuestionWithAnswers::toModel)
}
