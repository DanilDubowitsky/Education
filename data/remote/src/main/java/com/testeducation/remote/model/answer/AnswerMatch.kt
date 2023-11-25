package com.testeducation.remote.model.answer

import com.google.gson.annotations.SerializedName

data class AnswerMatch(
    @SerializedName("title")
    val title: String
)