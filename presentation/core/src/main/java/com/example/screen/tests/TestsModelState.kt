package com.example.screen.tests

import com.example.domain.model.test.TestShort

data class TestsModelState(
    private val tests: List<TestShort> = emptyList()
)
