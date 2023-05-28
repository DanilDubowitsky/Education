package com.testeducation.screen.auth.login

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.auth.login.LoginState

class LoginReducer : IReducer<LoginModelState, LoginState> {

    override fun reduce(modelState: LoginModelState): LoginState {
        return LoginState(
            isLoading = modelState.loadingState == LoginModelState.LoadingState.LOADING
        )
    }

}
