package com.example.screen.auth.login

data class LoginModelState(
    val email: String? = null,
    val password: String? = null,
    val loadingState: LoadingState = LoadingState.IDLE
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
