package com.testeducation.screen.home

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.home.HomeState

class HomeReducer : IReducer<HomeModelState, HomeState> {

    override fun reduce(modelState: HomeModelState): HomeState {
        return HomeState(
            selectedNavigationItem = modelState.selectedScreen.toUI()
        )
    }

    private fun HomeModelState.BottomNavigationItems.toUI() = when(this) {
        HomeModelState.BottomNavigationItems.TESTS -> HomeState.NavigationItem.MAIN
        HomeModelState.BottomNavigationItems.FAVORITES -> HomeState.NavigationItem.FAVORITES
        HomeModelState.BottomNavigationItems.LIBRARY -> HomeState.NavigationItem.LIBRARY
        HomeModelState.BottomNavigationItems.PROFILE -> HomeState.NavigationItem.PROFILE
    }
}
