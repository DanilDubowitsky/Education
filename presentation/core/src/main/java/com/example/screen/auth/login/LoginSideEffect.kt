package com.example.screen.auth.login

sealed interface LoginSideEffect {

    object InvalidPasswordError : LoginSideEffect

    object LoginInvalidError : LoginSideEffect
}
