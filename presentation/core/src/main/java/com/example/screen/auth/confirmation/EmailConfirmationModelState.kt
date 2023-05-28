package com.example.screen.auth.confirmation

data class EmailConfirmationModelState(
    val loadingState: LoadingState = LoadingState.IDLE,
    val code: String = ""
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}