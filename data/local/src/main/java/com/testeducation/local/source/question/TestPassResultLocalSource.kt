package com.testeducation.local.source.question

import com.testeducation.core.source.local.question.ITestPassResultLocalSource
import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.local.converter.toCompound
import com.testeducation.local.converter.toModel
import com.testeducation.local.dao.question.TestPassResultDao

class TestPassResultLocalSource(
    private val answeredQuestionDao: TestPassResultDao
) : ITestPassResultLocalSource {

    override suspend fun addTestPassResult(testId: String, result: TestPassResult) {
        answeredQuestionDao.insertOrUpdate(result.toCompound(testId))
    }

    override suspend fun getPassResult(testId: String): TestPassResult =
        answeredQuestionDao.getTestPassResult(testId).toModel()
}
