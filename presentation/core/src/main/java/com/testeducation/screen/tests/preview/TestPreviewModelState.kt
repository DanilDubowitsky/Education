package com.testeducation.screen.tests.preview

import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestShort

data class TestPreviewModelState(
    val loadingState: LoadingState = LoadingState.LOADING,
    val test: Test? = null,
    val isDescriptionExpanded: Boolean = false,
    val authorTests: List<TestShort> = emptyList()
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
