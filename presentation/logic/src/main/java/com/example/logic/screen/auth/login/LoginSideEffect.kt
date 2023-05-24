package com.example.logic.screen.auth.login

sealed interface LoginSideEffect {

    object InvalidPasswordError : LoginSideEffect

    object LoginInvalidError : LoginSideEffect

    data class ErrorTextInput(val mapError: Map<String, String>) : LoginSideEffect

    data class DisableTextInput(val mapDisabled: Map<String, Boolean>) : LoginSideEffect
}
