package com.testeducation.remote.model.answer

import com.google.gson.annotations.SerializedName

data class InputUserAnswerDataRemote(
    @SerializedName("question_id")
    val questionId: String,
    @SerializedName("answers")
    val answerIds: List<String>,
    @SerializedName("is_currectly")
    val isCorrect: Boolean?,
    @SerializedName("time_spent")
    val spentTime: Long,
    @SerializedName("custom_answer")
    val customAnswer: String?
)
