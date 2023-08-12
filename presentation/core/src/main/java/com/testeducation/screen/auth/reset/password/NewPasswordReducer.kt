package com.testeducation.screen.auth.reset.password

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.auth.reset.password.NewPasswordState

class NewPasswordReducer : IReducer<NewPasswordModelState, NewPasswordState> {

    override fun reduce(modelState: NewPasswordModelState): NewPasswordState {

        return NewPasswordState(
            isLoading = modelState.loadingState == NewPasswordModelState.LoadingState.LOADING
        )
    }

}
