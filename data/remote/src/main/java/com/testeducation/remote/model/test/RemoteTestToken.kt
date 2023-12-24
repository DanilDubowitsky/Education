package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName

data class RemoteTestToken(
    @SerializedName("token")
    val token: String,
    @SerializedName("code")
    val code: String
)
