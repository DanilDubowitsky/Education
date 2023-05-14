package com.example.screen.home

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.helper.error.IErrorHandler

class HomeViewModel(
    reducer: IReducer<HomeModelState, HomeState>,
    errorHandler: IErrorHandler
) : BaseViewModel<HomeModelState, HomeState, HomeSideEffect>(
    reducer, errorHandler
) {

    override val initialModelState: HomeModelState = HomeModelState()

}
