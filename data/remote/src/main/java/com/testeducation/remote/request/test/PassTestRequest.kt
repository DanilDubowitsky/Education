package com.testeducation.remote.request.test

import com.google.gson.annotations.SerializedName
import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.remote.model.answer.InputUserAnswerDataRemote
import com.testeducation.remote.model.result.RemotePassResult

data class PassTestRequest(
    @SerializedName("test_id")
    val testId: String,
    @SerializedName("user_answers")
    val answers: List<InputUserAnswerDataRemote>,
    @SerializedName("time_spent")
    val spentTime: Long,
    @SerializedName("was_cheating")
    val isCheating: Boolean,
    @SerializedName("result")
    val result: RemotePassResult.Result
)
