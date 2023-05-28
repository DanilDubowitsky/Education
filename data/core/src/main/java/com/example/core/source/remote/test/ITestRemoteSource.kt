package com.example.core.source.remote.test

import com.example.domain.model.test.TestShort

interface ITestRemoteSource {

    suspend fun getTests(): List<TestShort>

}