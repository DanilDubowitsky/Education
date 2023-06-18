package com.testeducation.remote.model.internal

import com.google.gson.annotations.SerializedName

data class RemoteAppVersion(
    @SerializedName("actual_version")
    val actualVersion: String,
    @SerializedName("min_version")
    val minVersion: String
)
