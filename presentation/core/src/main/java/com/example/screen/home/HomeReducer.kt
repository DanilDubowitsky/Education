package com.example.screen.home

import com.example.core.IReducer

class HomeReducer : IReducer<HomeModelState, HomeState> {

    override fun reduce(modelState: HomeModelState): HomeState {
        return HomeState()
    }
}
