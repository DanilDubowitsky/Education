package com.testeducation.screen.auth.confirmation

data class CodeConfirmationModelState(
    val loadingState: LoadingState = LoadingState.IDLE,
    val code: String? = null,
    val isCodeExpired: Boolean = false
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}