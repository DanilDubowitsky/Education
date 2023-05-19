package com.example.screen.home

data class HomeModelState(
    val navigationItems: BottomNavigationItems = BottomNavigationItems.MAIN
) {

    enum class BottomNavigationItems {
        MAIN,
        FAVORITES,
        SETTINGS,
        PROFILE
    }
}
