package com.testeducation.logic.screen.auth.confirmation

data class CodeConfirmationState(
    val isLoading: Boolean,
    val isCodeExpired: Boolean
)
