package com.testeducation.remote.request.question

import com.google.gson.annotations.SerializedName

data class AnswerMatch(
    @SerializedName("title")
    val title: String
)