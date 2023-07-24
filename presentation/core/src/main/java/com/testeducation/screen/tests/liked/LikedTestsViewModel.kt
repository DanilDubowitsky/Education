package com.testeducation.screen.tests.liked

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.liked.LikedTestsSideEffect
import com.testeducation.logic.screen.tests.liked.LikedTestsState

class LikedTestsViewModel(
    reducer: IReducer<LikedTestsModelState, LikedTestsState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<LikedTestsModelState, LikedTestsState, LikedTestsSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: LikedTestsModelState = LikedTestsModelState()

}