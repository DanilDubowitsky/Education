package com.testeducation.screen.profile

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.profile.ProfileState

class ProfileReducer: IReducer<ProfileModelState, ProfileState> {
    override fun reduce(modelState: ProfileModelState): ProfileState {
        return ProfileState(
            userName = modelState.user?.userName.orEmpty()
        )
    }
}