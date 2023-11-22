package com.testeducation.screen.profile.avatar

import com.testeducation.domain.model.profile.AvatarData

data class ProfileAvatarChangerModelState(
    val listAvatarId: List<AvatarData> = emptyList()
)