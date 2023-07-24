package com.testeducation.screen.tests.liked

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.liked.LikedTestsState

class LikedTestsReducer : IReducer<LikedTestsModelState, LikedTestsState> {

    override fun reduce(modelState: LikedTestsModelState): LikedTestsState {
        return LikedTestsState()
    }

}
