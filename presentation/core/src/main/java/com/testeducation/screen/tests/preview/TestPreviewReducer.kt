package com.testeducation.screen.tests.preview

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.preview.TestPreviewState

class TestPreviewReducer : IReducer<TestPreviewModelState, TestPreviewState> {

    override fun reduce(modelState: TestPreviewModelState): TestPreviewState {
        return TestPreviewState(
            isLoading = modelState.loadingState == TestPreviewModelState.LoadingState.LOADING
        )
    }
}
