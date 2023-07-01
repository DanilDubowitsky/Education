package com.testeducation.logic.model.test

import java.io.Serializable

data class TestFiltersUI(
    val minTime: Int,
    val maxTime: Int,
    val hasLimit: Boolean,
    val minQuestions: Int,
    val maxQuestions: Int,
    val selectedTheme: String?
) : Serializable
