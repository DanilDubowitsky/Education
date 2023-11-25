package com.testeducation.logic.screen.tests.pass

sealed interface TestPassingSideEffect {
    data class StartQuestionTimer(
        val time: Long
    ) : TestPassingSideEffect

    data class StartTestTimer(
        val time: Long
    ) : TestPassingSideEffect

    object EndTimer : TestPassingSideEffect
}
