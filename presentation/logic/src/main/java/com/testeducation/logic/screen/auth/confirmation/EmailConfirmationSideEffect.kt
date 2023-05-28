package com.testeducation.logic.screen.auth.confirmation

sealed interface EmailConfirmationSideEffect {
    object CodeValidationError : EmailConfirmationSideEffect
}
