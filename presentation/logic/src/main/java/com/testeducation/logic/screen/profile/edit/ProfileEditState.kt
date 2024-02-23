package com.testeducation.logic.screen.profile.edit

data class ProfileEditState(
    val email: String,
    val nickName: String,
    val avatar: Int,
    val isLoading: Boolean
)