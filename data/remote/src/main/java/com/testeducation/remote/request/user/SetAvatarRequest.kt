package com.testeducation.remote.request.user

import com.google.gson.annotations.SerializedName

data class SetAvatarRequest(
    @SerializedName("avatar")
    val avatar: Int
)