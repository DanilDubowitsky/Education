package com.testeducation.logic.screen.profile.avatar

import com.testeducation.logic.model.test.AvatarItemUI

data class ProfileAvatarChangerState(
    val avatarItemList: List<AvatarItemUI>,
    val isLoading: Boolean
)