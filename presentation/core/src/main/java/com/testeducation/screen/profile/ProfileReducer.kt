package com.testeducation.screen.profile

import com.testeducation.core.IReducer
import com.testeducation.helper.avatar.IAvatarHelper
import com.testeducation.logic.screen.profile.ProfileState

class ProfileReducer(private val avatarHelper: IAvatarHelper): IReducer<ProfileModelState, ProfileState> {
    override fun reduce(modelState: ProfileModelState): ProfileState {
        return ProfileState(
            userName = modelState.user?.userName.orEmpty(),
            createdTestCount = modelState.userStatistics?.createdTestCount ?: 0,
            passedTestCount = modelState.userStatistics?.passedTestCount ?: 0,
            drawableAvatar = avatarHelper.getAvatarDrawable(modelState.user?.avatarId)
        )
    }
}