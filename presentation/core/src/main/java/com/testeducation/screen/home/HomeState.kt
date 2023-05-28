package com.testeducation.screen.home

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
