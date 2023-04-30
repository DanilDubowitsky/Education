package com.example.remote.model.auth

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("fields")
    val fields: List<String>
)
