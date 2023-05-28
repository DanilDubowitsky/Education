package com.testeducation.core.repository.test

import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.repository.test.ITestRepository

class TestRepository(
    private val testRemoteSource: ITestRemoteSource
) : ITestRepository {

    override suspend fun getTests(): List<TestShort> =
        testRemoteSource.getTests()

}
