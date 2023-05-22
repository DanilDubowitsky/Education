package com.example.screen.auth.confirmation

import com.example.core.IReducer
import com.example.logic.screen.auth.confirmation.EmailConfirmationState

class EmailConfirmationReducer : IReducer<EmailConfirmationModelState, EmailConfirmationState> {

    override fun reduce(modelState: EmailConfirmationModelState): EmailConfirmationState {

        return EmailConfirmationState(
            isLoading = modelState.loadingState == EmailConfirmationModelState.LoadingState.LOADING
        )
    }
}
