package com.testeducation.screen.tests.preview

data class TestPreviewModelState(
    val loadingState: LoadingState = LoadingState.LOADING
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
