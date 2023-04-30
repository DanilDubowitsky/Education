package com.example.remote.model

import com.example.remote.model.auth.Status
import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("status")
    val status: Status?,
    @SerializedName("data")
    val data: T?
)
