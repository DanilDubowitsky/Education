package com.testeducation.screen.auth.confirmation

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.auth.confirmation.CodeConfirmationState

class CodeConfirmationReducer : IReducer<CodeConfirmationModelState, CodeConfirmationState> {

    override fun reduce(modelState: CodeConfirmationModelState): CodeConfirmationState {

        return CodeConfirmationState(
            isLoading = modelState.loadingState == CodeConfirmationModelState.LoadingState.LOADING,
            isCodeExpired = modelState.isCodeExpired
        )
    }
}
