package com.testeducation.logic.screen.tests.library

import com.testeducation.logic.model.test.TestShortUI

data class LibraryState(
    val isLoading: Boolean,
    val draftsTests: List<TestShortUI>,
    val publishedTests: List<TestShortUI>,
    val passedTests: List<TestShortUI>,
    val isRefreshing: Boolean
)