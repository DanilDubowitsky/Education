package com.example.domain.repository.test

import com.example.domain.model.test.TestShort

interface ITestRepository {

    suspend fun getTests(): List<TestShort>
}
