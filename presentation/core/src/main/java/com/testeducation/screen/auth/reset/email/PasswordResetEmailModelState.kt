package com.testeducation.screen.auth.reset.email

data class PasswordResetEmailModelState(
    val loadingState: LoadingState = LoadingState.IDLE,
    val inputEmail: String = ""
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
