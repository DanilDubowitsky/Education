package com.testeducation.screen.home

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.home.HomeState

class HomeReducer : IReducer<HomeModelState, HomeState> {

    override fun reduce(modelState: HomeModelState): HomeState {
        return HomeState(
            selectedNavigationItem = modelState.navigationItems.toUI()
        )
    }

    private fun HomeModelState.BottomNavigationItems.toUI() = when(this) {
        HomeModelState.BottomNavigationItems.TESTS -> HomeState.NavigationItem.MAIN
        HomeModelState.BottomNavigationItems.FAVORITES -> HomeState.NavigationItem.FAVORITES
        HomeModelState.BottomNavigationItems.SETTINGS -> HomeState.NavigationItem.SETTINGS
        HomeModelState.BottomNavigationItems.PROFILE -> HomeState.NavigationItem.PROFILE
    }
}
