package com.testeducation.screen.tests.liked

import com.testeducation.domain.model.test.TestShort

data class LikedTestsModelState(
    val tests: List<TestShort> = emptyList()
)
