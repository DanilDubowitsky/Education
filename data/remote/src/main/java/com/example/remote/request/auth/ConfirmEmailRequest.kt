package com.example.remote.request.auth

import com.google.gson.annotations.SerializedName

class ConfirmEmailRequest(
    @SerializedName("code")
    val code: String
)
