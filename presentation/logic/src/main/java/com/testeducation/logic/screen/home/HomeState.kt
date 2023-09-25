package com.testeducation.logic.screen.home

data class HomeState(
    val selectedNavigationItem: NavigationItem
) {

    enum class NavigationItem {
        MAIN,
        FAVORITES,
        LIBRARY,
        PROFILE
    }
}
