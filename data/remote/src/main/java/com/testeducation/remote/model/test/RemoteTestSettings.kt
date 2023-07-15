package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName

data class RemoteTestSettings(
    @SerializedName("availability")
    val availability: String,
    @SerializedName("allow_preview_questions")
    val previewQuestions: Boolean
)
