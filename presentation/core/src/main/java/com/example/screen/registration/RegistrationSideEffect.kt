package com.example.screen.registration

sealed interface RegistrationSideEffect {

    data class ShowMessage(
        val message: String
    ) : RegistrationSideEffect

    object ShowSuccessRegistration : RegistrationSideEffect
}
