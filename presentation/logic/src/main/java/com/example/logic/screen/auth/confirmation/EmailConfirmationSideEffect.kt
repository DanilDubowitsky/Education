package com.example.logic.screen.auth.confirmation

sealed interface EmailConfirmationSideEffect {
    object CodeValidationError : EmailConfirmationSideEffect
}
