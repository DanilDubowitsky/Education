package com.testeducation.screen.profile.avatar

import com.testeducation.core.IReducer
import com.testeducation.helper.avatar.IAvatarHelper
import com.testeducation.logic.screen.profile.avatar.ProfileAvatarChangerState

class ProfileAvatarChangerReducer constructor(private val avatarHelper: IAvatarHelper) :
    IReducer<ProfileAvatarChangerModelState, ProfileAvatarChangerState> {
    override fun reduce(modelState: ProfileAvatarChangerModelState): ProfileAvatarChangerState {
        return ProfileAvatarChangerState(
            avatarItemList = avatarHelper.convertAvatarDataToUi(
                modelState.listAvatarId
            )
        )
    }
}