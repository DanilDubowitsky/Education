package com.example.screen.registration

import com.example.core.IReducer

class RegistrationReducer : IReducer<RegistrationModelState, RegistrationState> {

    override fun reduce(modelState: RegistrationModelState): RegistrationState {
        return RegistrationState(
            modelState.loadingState == RegistrationModelState.LoadingState.LOADING
        )
    }

}
