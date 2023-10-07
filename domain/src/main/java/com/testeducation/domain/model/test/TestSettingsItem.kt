package com.testeducation.domain.model.test

data class TestSettingsItem(
    val availability: TestAvailability,
    val testQuestionOrder: TestQuestionOrder,
    val showResultMode: TestShowResultMode,
    val minCorrectAnswer: Int,
    /** Доступен ли просмотр вопросов перед прохождением для иных пользователей */
    val allowPreviewQuestions: Boolean,
    /** Включить или выключить запрет на выход приложения в фоновый режим */
    val antiCheating: Boolean
)