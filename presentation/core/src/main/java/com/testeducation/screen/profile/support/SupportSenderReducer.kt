package com.testeducation.screen.profile.support

import com.testeducation.core.IReducer
import com.testeducation.domain.model.profile.CategorySupport
import com.testeducation.logic.screen.profile.support.SupportSenderState

class SupportSenderReducer :
    IReducer<SupportSenderModelState, SupportSenderState> {
    override fun reduce(modelState: SupportSenderModelState): SupportSenderState {
        return SupportSenderState(
            categorySelected = modelState.categorySupport.convert(),
            isLoading = modelState.isLoading
        )
    }

    private fun CategorySupport.convert() = when (this) {
        CategorySupport.Bug -> SupportSenderState.CategoryUi.Bug
        CategorySupport.Message -> SupportSenderState.CategoryUi.Message
        CategorySupport.Wish -> SupportSenderState.CategoryUi.Wish
    }

}