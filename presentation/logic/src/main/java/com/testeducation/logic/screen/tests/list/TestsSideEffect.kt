package com.testeducation.logic.screen.tests.list

sealed interface TestsSideEffect {
    object AddScrollListener : TestsSideEffect
    object RemoveScrollListener : TestsSideEffect
}
