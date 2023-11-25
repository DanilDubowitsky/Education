package com.testeducation.remote.model.answer

import com.google.gson.annotations.SerializedName

data class RemoteAnswer(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("current")
    val current: Boolean? = false,
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("match")
    val match: AnswerMatch? = null,
    @SerializedName("text")
    val text: String? = null
)