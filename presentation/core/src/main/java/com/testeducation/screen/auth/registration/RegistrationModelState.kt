package com.testeducation.screen.auth.registration

data class RegistrationModelState(
    val loadingState: LoadingState = LoadingState.IDLE,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val userName: String? = null
) {

    enum class LoadingState {
        IDLE,
        LOADING
    }
}
