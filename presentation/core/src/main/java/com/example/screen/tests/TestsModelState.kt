package com.example.screen.tests

import com.example.domain.model.test.TestShort
import com.example.domain.model.theme.ThemeShort
import com.example.domain.model.user.User

data class TestsModelState(
    val user: User? = null,
    val tests: List<TestShort> = emptyList(),
    val themes: List<ThemeShort> = emptyList(),
    val loadingState: LoadingState = LoadingState.LOADING
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
