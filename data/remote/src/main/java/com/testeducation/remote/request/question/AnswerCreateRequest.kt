package com.testeducation.remote.request.question

import com.google.gson.annotations.SerializedName

data class AnswerCreateRequest(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("media")
    val media: String = "",
    @SerializedName("current")
    val current: Boolean = false,
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("match")
    val match: AnswerMatch? = null,
    @SerializedName("text")
    val text: String? = null
)