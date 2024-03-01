package com.testeducation.remote.model.user

import com.google.gson.annotations.SerializedName

data class ConfirmUserRemote(
    @SerializedName("code")
    val code: String,
    @SerializedName("token")
    val token: String,
)