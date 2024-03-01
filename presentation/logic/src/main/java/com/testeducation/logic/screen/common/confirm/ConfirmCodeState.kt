package com.testeducation.logic.screen.common.confirm

data class ConfirmCodeState(
    val code: String,
    val title: String,
    val description: String,
    val isSendCodeRetry: Boolean,
    val isLoading: Boolean
)