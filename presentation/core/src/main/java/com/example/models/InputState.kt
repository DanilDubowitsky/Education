package com.example.models

sealed class InputState {
    object Default : InputState()
    class Error(val errorText: String) : InputState()
    object Disabled: InputState()
}