package com.testeducation.screen.auth.confirmation

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.auth.confirmation.EmailConfirmationState

class EmailConfirmationReducer : IReducer<EmailConfirmationModelState, EmailConfirmationState> {

    override fun reduce(modelState: EmailConfirmationModelState): EmailConfirmationState {

        return EmailConfirmationState(
            isLoading = modelState.loadingState == EmailConfirmationModelState.LoadingState.LOADING
        )
    }
}
