package com.example.screen.tests.creation

import com.example.domain.model.theme.ThemeShort

data class TestCreationModelState(
    val themes: List<ThemeShort> = emptyList(),
    val loadingState: LoadingState = LoadingState.LOADING
) {
    enum class LoadingState {
        LOADING,
        IDLE
    }
}