package com.example.screen.auth.confirmation

import com.example.core.IReducer

class EmailConfirmationReducer : IReducer<EmailConfirmationModelState, EmailConfirmationState> {

    override fun reduce(modelState: EmailConfirmationModelState): EmailConfirmationState {

        return EmailConfirmationState(
            isLoading = modelState.loadingState == EmailConfirmationModelState.LoadingState.LOADING
        )
    }
}
