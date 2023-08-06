package com.testeducation.logic.screen.auth.confirmation

sealed interface EmailConfirmationSideEffect {
    object CodeValidationError : EmailConfirmationSideEffect
    object RegistrationSuccess : EmailConfirmationSideEffect
    class StartTimer(
        val expireTime: Long
    ) : EmailConfirmationSideEffect
}
