package com.example.screen.auth.login

import com.example.logic.model.common.InputState

data class LoginModelState(
    val email: String = "",
    val password: String = "",
    val loadingState: LoadingState = LoadingState.IDLE
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
