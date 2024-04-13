package com.testeducation.domain.model.result

data class TestPassResult(
    val id: String,
    val testId: String,
    val answers: List<UserAnswer>,
    val timeSpent: Long,
    val wasCheating: Boolean,
    val success: Boolean
)
