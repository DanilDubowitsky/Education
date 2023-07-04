package com.testeducation.logic.model.test

import java.io.Serializable

data class TestFiltersUI(
    val minTime: String,
    val maxTime: String,
    val hasLimit: Boolean,
    val minQuestions: String,
    val maxQuestions: String,
    val selectedTheme: String?,
    val orderFieldUI: TestOrderFieldUI,
    val preLoadedTests: List<TestShortUI>,
    val currentItemsCount: Int
) : Serializable
