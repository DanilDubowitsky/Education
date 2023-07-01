package com.testeducation.logic.screen.tests.filters

sealed interface TestsFiltersSideEffect {
    data class SetTextFilters(
        val minQuestionCount: String,
        val maxQuestionsCount: String,
        val maxTimeLimit: String,
        val minTimeLimit: String
    ) : TestsFiltersSideEffect
}