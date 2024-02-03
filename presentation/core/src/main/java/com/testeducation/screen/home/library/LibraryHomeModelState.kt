package com.testeducation.screen.home.library

data class LibraryHomeModelState(
    val currentScreen: Screen? = null
) {

    enum class Screen {
        TEST_LIBRARY
    }
}
