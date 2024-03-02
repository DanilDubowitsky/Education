package com.testeducation.screen.common.confirm

data class ConfirmCodeModelState(
    val title: String,
    val description: String,
    val code: String = "",
    val sendCodeRetry: Boolean = false,
    val token: String = "",
    val isLoading: Boolean = true
)