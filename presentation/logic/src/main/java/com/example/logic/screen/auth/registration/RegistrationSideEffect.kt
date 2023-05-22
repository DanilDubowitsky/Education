package com.example.logic.screen.auth.registration

sealed interface RegistrationSideEffect {

    data class ShowMessage(
        val message: String
    ) : RegistrationSideEffect

    object ShowSuccessRegistration : RegistrationSideEffect
}
