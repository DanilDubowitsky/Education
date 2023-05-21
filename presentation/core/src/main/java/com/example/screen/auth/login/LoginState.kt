package com.example.screen.auth.login

import com.example.models.InputState

data class LoginState(
    val isLoading: Boolean,
    val emailInputState: InputState
)
