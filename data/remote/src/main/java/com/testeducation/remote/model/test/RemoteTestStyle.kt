package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName

data class RemoteTestStyle(
    @SerializedName("color")
    val color: String,
    @SerializedName("background")
    val background: String
)
