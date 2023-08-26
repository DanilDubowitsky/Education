package com.testeducation.remote.request.question

import com.google.gson.annotations.SerializedName

data class QuestionCreateRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("time")
    val time: Int,
    @SerializedName("media")
    val media: String = "",
    @SerializedName("answers")
    val answers: List<AnswerCreateRequest>
)