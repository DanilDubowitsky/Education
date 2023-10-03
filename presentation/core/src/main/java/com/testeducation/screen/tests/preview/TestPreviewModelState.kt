package com.testeducation.screen.tests.preview

import com.testeducation.domain.model.test.Test

data class TestPreviewModelState(
    val loadingState: LoadingState = LoadingState.LOADING,
    val test: Test? = null
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
