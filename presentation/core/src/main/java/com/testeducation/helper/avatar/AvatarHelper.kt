package com.testeducation.helper.avatar

import com.testeducation.domain.model.profile.AvatarData
import com.testeducation.helper.resource.DrawableResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.model.test.AvatarItemUI

class AvatarHelper(
    private val resourceHelper: IResourceHelper
): IAvatarHelper {

    override fun getAvatarDrawable(avatarId: Int?): Int = getAvatarById(avatarId ?: 0)

    override fun convertAvatarDataToUi(list: List<AvatarData>): List<AvatarItemUI> {
        return list.map {
            AvatarItemUI(
                id = it.id,
                avatarDrawableId = getAvatarById(avatarId = it.id),
                isSelected = it.isSelected
            )
        }
    }

    private fun getAvatarById(avatarId: Int) : Int {
        return when(avatarId) {
            1 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.First)
            2 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Second)
            3 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Three)
            4 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Four)
            5 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Five)
            6 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Six)
            7 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Seven)
            8 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Eight)
            else -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Default)
        }
    }
}