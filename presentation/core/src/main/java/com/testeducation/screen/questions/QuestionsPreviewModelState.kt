package com.testeducation.screen.questions

import com.testeducation.domain.model.question.Question

data class QuestionsPreviewModelState(
    val questions: List<Question> = emptyList(),
    val loadingState: LoadingState = LoadingState.LOADING
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}
