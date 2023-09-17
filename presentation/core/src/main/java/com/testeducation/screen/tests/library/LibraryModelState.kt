package com.testeducation.screen.tests.library

import com.testeducation.domain.model.test.TestShort

data class LibraryModelState(
    val publishedTests: List<TestShort> = emptyList(),
    val passedTests: List<TestShort> = emptyList(),
    val draftsTests: List<TestShort> = emptyList(),
    val loadingState: LoadingState = LoadingState.LOADING
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
