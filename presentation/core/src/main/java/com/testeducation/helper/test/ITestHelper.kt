package com.testeducation.helper.test

import com.testeducation.domain.model.test.TestShort

interface ITestHelper {

    suspend fun toggleTestLike(position: Int, tests: List<TestShort>): List<TestShort>
}