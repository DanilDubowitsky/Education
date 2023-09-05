package com.testeducation.logic.screen.tests.liked

sealed interface LikedTestsSideEffect {
    object OnLoadReady : LikedTestsSideEffect
}