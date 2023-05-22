package com.example.screen.home

data class HomeModelState(
    val navigationItems: BottomNavigationItems = BottomNavigationItems.TESTS
) {

    enum class BottomNavigationItems {
        TESTS,
        FAVORITES,
        SETTINGS,
        PROFILE
    }
}
