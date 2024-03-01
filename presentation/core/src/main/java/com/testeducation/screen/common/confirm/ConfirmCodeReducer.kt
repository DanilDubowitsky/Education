package com.testeducation.screen.common.confirm

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.common.confirm.ConfirmCodeState

class ConfirmCodeReducer: IReducer<ConfirmCodeModelState, ConfirmCodeState> {
    override fun reduce(modelState: ConfirmCodeModelState): ConfirmCodeState {
        return ConfirmCodeState(
            code = modelState.code,
            title = modelState.title,
            description = modelState.description,
            isSendCodeRetry = modelState.sendCodeRetry,
            isLoading = modelState.isLoading
        )
    }
}
