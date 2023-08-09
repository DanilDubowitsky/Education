package com.testeducation.logic.screen.auth.confirmation

sealed interface CodeConfirmationSideEffect {
    object CodeValidationError : CodeConfirmationSideEffect
    object RegistrationSuccess : CodeConfirmationSideEffect
    class StartTimer(
        val expireTime: Long
    ) : CodeConfirmationSideEffect
}
