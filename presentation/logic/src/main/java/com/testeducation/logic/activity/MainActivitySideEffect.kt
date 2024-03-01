package com.testeducation.logic.activity

sealed interface MainActivitySideEffect {
    object OnDataLoaded : MainActivitySideEffect
}
