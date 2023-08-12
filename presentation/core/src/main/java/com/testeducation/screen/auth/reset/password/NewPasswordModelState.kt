package com.testeducation.screen.auth.reset.password

data class NewPasswordModelState(
    val password: String = "",
    val repeatedPassword: String = "",
    val loadingState: LoadingState = LoadingState.IDLE
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
