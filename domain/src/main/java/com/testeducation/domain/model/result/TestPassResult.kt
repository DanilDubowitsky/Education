package com.testeducation.domain.model.result

data class TestPassResult(
    val id: String,
    val testId: String,
    val answers: List<UserAnswer>,
    val timeSpent: Long,
    val wasCheating: Boolean,
    val result: ResultStatus
) {

    enum class ResultStatus {
        SUCCESSFUL,
        FAILED,
        FAILED_MIN_QUESTIONS,
        FAILED_CHEATING
    }
}
