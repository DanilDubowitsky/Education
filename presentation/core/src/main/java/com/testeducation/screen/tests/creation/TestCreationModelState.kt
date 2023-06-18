package com.testeducation.screen.tests.creation

import com.testeducation.domain.model.theme.ThemeShort

data class TestCreationModelState(
    val themes: List<ThemeShort> = emptyList(),
    val loadingState: LoadingState = LoadingState.LOADING,
    val stepState: StepState = StepState.FIRST
) {
    enum class LoadingState {
        LOADING,
        IDLE
    }

    enum class StepState {
        FIRST, SECOND;

        companion object {
            fun StepState.isFirst() = this == FIRST
        }
    }
}