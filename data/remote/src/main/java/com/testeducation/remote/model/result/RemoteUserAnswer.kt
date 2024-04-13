package com.testeducation.remote.model.result

import com.google.gson.annotations.SerializedName
import com.testeducation.remote.model.question.RemoteQuestion

data class RemoteUserAnswer(
    @SerializedName("id")
    val id: String,
    @SerializedName("question")
    val question: RemoteQuestion,
    @SerializedName("answers")
    val answers: List<String>,
    @SerializedName("custom_answer")
    val customAnswer: String?,
    @SerializedName("time_spent")
    val timeSpent: Long,
    @SerializedName("is_correctly")
    val isCorrect: Boolean
)
