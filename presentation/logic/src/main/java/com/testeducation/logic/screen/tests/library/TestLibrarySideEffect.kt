package com.testeducation.logic.screen.tests.library

sealed interface TestLibrarySideEffect {
    object OnLoadReady : TestLibrarySideEffect
}
