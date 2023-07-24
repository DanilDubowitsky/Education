package com.testeducation.remote.model.global

import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("status")
    val status: Status?,
    @SerializedName("data")
    val data: T
)
