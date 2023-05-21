package com.example.screen.auth.login

import com.example.models.InputState

data class LoginModelState(
    val email: String? = null,
    val password: String? = null,
    val loadingState: LoadingState = LoadingState.IDLE,
    val emailInputState: InputState = InputState.Default
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
