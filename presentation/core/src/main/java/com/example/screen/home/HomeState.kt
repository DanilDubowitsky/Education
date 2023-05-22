package com.example.screen.home

data class HomeState(
    val selectedNavigationItem: NavigationItem
) {

    enum class NavigationItem {
        MAIN,
        FAVORITES,
        SETTINGS,
        PROFILE
    }
}
