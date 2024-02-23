package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName

data class RemoteTestSettings(
    @SerializedName("availability")
    val availability: String,
    @SerializedName("allow_preview_questions")
    val previewQuestions: Boolean,
    @SerializedName("min_correct_answers")
    val minCorrectAnswers: Int,
    @SerializedName("anti_cheating")
    val antiCheating: Boolean,
    @SerializedName("time_limit")
    val timeLimit: Int,
    @SerializedName("questions_order")
    val questionsOrder: QuestionsOrder
) {

    enum class QuestionsOrder {
        Sequencial,
        Shuffled
    }
}
