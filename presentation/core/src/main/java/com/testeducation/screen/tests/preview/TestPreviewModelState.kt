package com.testeducation.screen.tests.preview

import com.testeducation.domain.model.test.Test

data class TestPreviewModelState(
    val loadingState: LoadingState = LoadingState.LOADING,
    val test: Test? = null,
    val isQuestionsShown: Boolean = false,
    val isDescriptionExpanded: Boolean = false
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
