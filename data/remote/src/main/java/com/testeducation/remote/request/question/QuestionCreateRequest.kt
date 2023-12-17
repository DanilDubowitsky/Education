package com.testeducation.remote.request.question

import com.google.gson.annotations.SerializedName
import com.testeducation.remote.request.question.answer.AnswerCreateRequest

data class QuestionCreateRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("time_limit")
    val time: Long,
    @SerializedName("media")
    val media: String = "",
    @SerializedName("answers")
    val answers: List<AnswerCreateRequest>
)