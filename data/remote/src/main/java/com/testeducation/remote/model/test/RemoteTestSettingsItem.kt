package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName

data class RemoteTestSettingsItem(
    @SerializedName("availability")
    val availability: RemoteTestAvailability? = null,
    @SerializedName("questions_order")
    val testQuestionOrder: RemoteTestQuestionOrder? = null,
    @SerializedName("show_result_mode")
    val showResultMode: RemoteTestShowResultMode? = null,
    @SerializedName("min_correct_answers")
    val minCorrectAnswer: Int? = null,
    /** Доступен ли просмотр вопросов перед прохождением для иных пользователей */
    @SerializedName("allow_preview_questions")
    val allowPreviewQuestions: Boolean? = null,
    /** Включить или выключить запрет на выход приложения в фоновый режим */
    @SerializedName("anti_cheating")
    val antiCheating: Boolean? = null
)