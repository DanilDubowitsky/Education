package com.testeducation.remote.request.test

import com.google.gson.annotations.SerializedName

data class TestCreationRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("theme")
    val themeId: String,
    @SerializedName("style")
    val style: TestStyleRequest
)