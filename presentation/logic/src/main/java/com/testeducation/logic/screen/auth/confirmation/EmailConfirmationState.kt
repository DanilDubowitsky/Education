package com.testeducation.logic.screen.auth.confirmation

data class EmailConfirmationState(
    val isLoading: Boolean,
    val isCodeExpired: Boolean
)
