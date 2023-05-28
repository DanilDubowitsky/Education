package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName

data class RemoteThemeShort(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
)
