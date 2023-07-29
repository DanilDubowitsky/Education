package com.testeducation.remote.request.test

import com.google.gson.annotations.SerializedName

data class TestStyleRequest(
    @SerializedName("color")
    val color: String,
    @SerializedName("background")
    val background: String
)