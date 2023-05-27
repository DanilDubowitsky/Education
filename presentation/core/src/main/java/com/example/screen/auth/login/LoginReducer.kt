package com.example.screen.auth.login

import com.example.core.IReducer
import com.example.logic.screen.auth.login.LoginState

class LoginReducer : IReducer<LoginModelState, LoginState> {

    override fun reduce(modelState: LoginModelState): LoginState {
        return LoginState(
            isLoading = modelState.loadingState == LoginModelState.LoadingState.LOADING
        )
    }

}
