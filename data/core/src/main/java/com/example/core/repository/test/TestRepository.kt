package com.example.core.repository.test

import com.example.core.source.remote.test.ITestRemoteSource
import com.example.domain.model.test.TestShort
import com.example.domain.repository.test.ITestRepository

class TestRepository(
    private val testRemoteSource: ITestRemoteSource
) : ITestRepository {

    override suspend fun getTests(): List<TestShort> =
        testRemoteSource.getTests()

}
