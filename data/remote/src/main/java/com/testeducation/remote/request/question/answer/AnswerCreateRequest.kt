package com.testeducation.remote.request.question.answer

import com.google.gson.annotations.SerializedName
import com.testeducation.remote.model.answer.AnswerMatch

data class AnswerCreateRequest(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("media")
    val media: String = "",
    @SerializedName("current")
    val current: Boolean = false,
    @SerializedName("order")
    val order: Int? = null,
    @SerializedName("match")
    val match: AnswerMatch? = null,
    @SerializedName("text")
    val text: String? = null
)