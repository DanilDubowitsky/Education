package com.testeducation.core.source.local.question

import com.testeducation.domain.model.result.TestPassResult

interface ITestPassResultLocalSource {

    suspend fun addTestPassResult(testId: String, result: TestPassResult)

    suspend fun getPassResult(testId: String): TestPassResult

}
