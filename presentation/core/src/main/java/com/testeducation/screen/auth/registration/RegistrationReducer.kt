package com.testeducation.screen.auth.registration

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.auth.registration.RegistrationState

class RegistrationReducer : IReducer<RegistrationModelState, RegistrationState> {

    override fun reduce(modelState: RegistrationModelState): RegistrationState {
        return RegistrationState(
            modelState.loadingState == RegistrationModelState.LoadingState.LOADING
        )
    }

}
