package com.example.screen.auth.login

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
