package com.testeducation.remote.model.question

import com.google.gson.annotations.SerializedName
import com.testeducation.remote.model.answer.RemoteAnswer
import com.testeducation.remote.request.question.AnswerCreateRequest

data class RemoteQuestion(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Type,
    @SerializedName("time")
    val time: Int,
    @SerializedName("answers")
    val answers: List<RemoteAnswer>,
    @SerializedName("order")
    val questionNumber: String
) {

    enum class Type {
        Match,
        Choice,
        Text,
        Reorder
    }
}