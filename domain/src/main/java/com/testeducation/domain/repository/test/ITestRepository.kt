package com.testeducation.domain.repository.test

import com.testeducation.domain.model.test.TestShort

interface ITestRepository {

    suspend fun getTests(): List<TestShort>
}
