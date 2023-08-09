package com.testeducation.screen.auth.reset

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.auth.reset.PasswordResetEmailState

class PasswordResetEmailReducer : IReducer<PasswordResetEmailModelState, PasswordResetEmailState> {

    override fun reduce(modelState: PasswordResetEmailModelState): PasswordResetEmailState =
        PasswordResetEmailState(
            isLoading = modelState.loadingState == PasswordResetEmailModelState.LoadingState.LOADING
        )
}
