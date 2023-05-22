package com.example.core.source.remote

import com.example.domain.model.test.TestShort

interface ITestRemoteSource {

    suspend fun getTests(): List<TestShort>

}