package com.example.logic.screen.auth.login

sealed interface LoginSideEffect {

    object InvalidPasswordError : LoginSideEffect

    object LoginInvalidError : LoginSideEffect
}
