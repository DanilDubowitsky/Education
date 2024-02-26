package com.testeducation.screen.common.confirm

data class ConfirmCodeModelState(
    val title: String,
    val description: String,
    val code: String = "",
)