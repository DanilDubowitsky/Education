package com.testeducation.logic.screen.tests.code.enter

sealed interface TestCodeEnterSideEffect {

    data class ShowCodeEnterError(
        val text: String
    ) : TestCodeEnterSideEffect
}
