package com.testeducation.screen.home

data class HomeModelState(
    val selectedScreen: BottomNavigationItems = BottomNavigationItems.TESTS
) {

    enum class BottomNavigationItems {
        TESTS,
        FAVORITES,
        LIBRARY,
        PROFILE
    }
}
