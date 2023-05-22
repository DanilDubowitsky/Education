package com.example.screen.auth.registration

import com.example.core.IReducer
import com.example.logic.screen.auth.registration.RegistrationState

class RegistrationReducer : IReducer<RegistrationModelState, RegistrationState> {

    override fun reduce(modelState: RegistrationModelState): RegistrationState {
        return RegistrationState(
            modelState.loadingState == RegistrationModelState.LoadingState.LOADING
        )
    }

}
