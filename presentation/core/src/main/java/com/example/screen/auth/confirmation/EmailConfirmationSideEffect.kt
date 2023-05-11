package com.example.screen.auth.confirmation

sealed interface EmailConfirmationSideEffect {
    object CodeValidationError : EmailConfirmationSideEffect
}
