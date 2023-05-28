package com.testeducation.logic.screen.auth.login

sealed interface LoginSideEffect {
    data class EmailInputError(val error: String) : LoginSideEffect

    data class PasswordInputError(val error: String) : LoginSideEffect
}
