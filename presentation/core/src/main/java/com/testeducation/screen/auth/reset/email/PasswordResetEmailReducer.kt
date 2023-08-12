package com.testeducation.screen.auth.reset.email

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.auth.reset.email.PasswordResetEmailState

class PasswordResetEmailReducer : IReducer<PasswordResetEmailModelState, PasswordResetEmailState> {

    override fun reduce(modelState: PasswordResetEmailModelState): PasswordResetEmailState =
        PasswordResetEmailState(
            isLoading = modelState.loadingState == PasswordResetEmailModelState.LoadingState.LOADING
        )
}
