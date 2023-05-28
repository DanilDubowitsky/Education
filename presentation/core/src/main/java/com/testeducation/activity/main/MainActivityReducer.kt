package com.testeducation.activity.main

import com.testeducation.logic.activity.MainActivityState
import com.testeducation.core.IReducer

class MainActivityReducer : IReducer<MainActivityModelState, MainActivityState> {

    override fun reduce(modelState: MainActivityModelState): MainActivityState {
        return MainActivityState()
    }

}
