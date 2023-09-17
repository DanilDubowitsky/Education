package com.testeducation.logic.screen.tests.library.tests

sealed interface TestLibrarySideEffect {
    object OnLoadReady : TestLibrarySideEffect
}
