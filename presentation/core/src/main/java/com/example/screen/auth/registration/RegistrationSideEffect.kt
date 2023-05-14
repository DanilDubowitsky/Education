package com.example.screen.auth.registration

sealed interface RegistrationSideEffect {

    data class ShowMessage(
        val message: String
    ) : RegistrationSideEffect

    object ShowSuccessRegistration : RegistrationSideEffect
}
