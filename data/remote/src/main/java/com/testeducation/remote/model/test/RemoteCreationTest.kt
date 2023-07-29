package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName
import com.testeducation.domain.model.test.TestStyle

data class RemoteCreationTest(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("style")
    val testStyle: TestStyle
)