package com.testeducation.screen.profile.about

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.profile.about.AboutAppState

class AboutAppReducer :
    IReducer<AboutAppModelState, AboutAppState> {
    override fun reduce(modelState: AboutAppModelState): AboutAppState {
        return AboutAppState()
    }
}