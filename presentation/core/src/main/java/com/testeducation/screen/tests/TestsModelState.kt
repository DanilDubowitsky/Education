package com.testeducation.screen.tests

import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.domain.model.user.User

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
