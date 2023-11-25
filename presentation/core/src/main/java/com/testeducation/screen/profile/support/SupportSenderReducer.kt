package com.testeducation.screen.profile.support

import com.testeducation.core.IReducer
import com.testeducation.domain.model.profile.CategorySupport
import com.testeducation.logic.screen.profile.support.SupportSenderState

class SupportSenderReducer :
    IReducer<SupportSenderModelState, SupportSenderState> {
    override fun reduce(modelState: SupportSenderModelState): SupportSenderState {
        return SupportSenderState(
            isBugCategory = modelState.categorySupport == CategorySupport.Bug,
            isLoading = modelState.isLoading
        )
    }

}