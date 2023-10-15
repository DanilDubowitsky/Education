package com.testeducation.domain.model.test

data class TestSettingsItem(
    val availability: TestAvailability? = null,
    val testQuestionOrder: TestQuestionOrder? = null,
    val showResultMode: TestShowResultMode? = null,
    val minCorrectAnswer: Int? = null,
    /** Доступен ли просмотр вопросов перед прохождением для иных пользователей */
    val allowPreviewQuestions: Boolean? = null,
    /** Включить или выключить запрет на выход приложения в фоновый режим */
    val antiCheating: Boolean? = null
)