package com.example.activity.main

import com.example.core.IReducer

class MainActivityReducer : IReducer<MainActivityModelState, MainActivityState> {

    override fun reduce(modelState: MainActivityModelState): MainActivityState {
        return MainActivityState()
    }

}
