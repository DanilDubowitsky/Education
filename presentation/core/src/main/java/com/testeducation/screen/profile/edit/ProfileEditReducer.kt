package com.testeducation.screen.profile.edit

import com.testeducation.core.IReducer
import com.testeducation.helper.avatar.IAvatarHelper
import com.testeducation.logic.screen.profile.edit.ProfileEditState

class ProfileEditReducer(private val avatarHelper: IAvatarHelper) :
    IReducer<ProfileEditModelState, ProfileEditState> {
    override fun reduce(modelState: ProfileEditModelState): ProfileEditState {
        return ProfileEditState(
            email = modelState.user?.email.orEmpty(),
            nickName = modelState.user?.userName.orEmpty(),
            avatar = avatarHelper.getAvatarDrawable(modelState.user?.avatarId ?: 0)
        )
    }
}