package com.testeducation.screen.common.confirm

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.common.confirm.ConfirmCodeState

class ConfirmCodeViewModel(
    reducer: IReducer<ConfirmCodeModelState, ConfirmCodeState>,
    exceptionHandler: IExceptionHandler,
    title: String,
    description: String,
) : BaseViewModel<ConfirmCodeModelState, ConfirmCodeState, Any>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: ConfirmCodeModelState = ConfirmCodeModelState(title, description)


}