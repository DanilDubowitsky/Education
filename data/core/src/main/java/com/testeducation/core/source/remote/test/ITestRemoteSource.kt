package com.testeducation.core.source.remote.test

import com.testeducation.domain.model.test.TestShort

interface ITestRemoteSource {

    suspend fun getTests(): List<TestShort>

}