package com.testeducation.helper.avatar

import com.testeducation.domain.model.profile.AvatarData
import com.testeducation.logic.model.test.AvatarItemUI

interface IAvatarHelper {
    fun getAvatarDrawable(avatarId: Int?): Int
    fun convertAvatarDataToUi(list: List<AvatarData>): List<AvatarItemUI>
}