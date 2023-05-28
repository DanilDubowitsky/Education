package com.example.screen.auth.registration

data class RegistrationModelState(
    val loadingState: LoadingState = LoadingState.IDLE
) {

    enum class LoadingState {
        IDLE,
        LOADING
    }
}
