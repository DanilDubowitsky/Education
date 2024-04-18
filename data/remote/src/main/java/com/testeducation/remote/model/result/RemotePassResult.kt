package com.testeducation.remote.model.result

import com.google.gson.annotations.SerializedName

data class RemotePassResult(
    @SerializedName("id")
    val id: String,
    @SerializedName("test_id")
    val testId: String,
    @SerializedName("answers")
    val answers: List<RemoteUserAnswer>,
    @SerializedName("time_spent")
    val timeSpent: Long,
    @SerializedName("was_cheating")
    val wasCheating: Boolean,
    @SerializedName("result")
    val result: Result
) {
    enum class Result {
        Successful,
        Failed,
        FailedMinQuestions,
        FailedCheating
    }
}
