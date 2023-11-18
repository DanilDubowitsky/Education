package com.testeducation.helper.avatar

import com.testeducation.helper.resource.DrawableResource
import com.testeducation.helper.resource.IResourceHelper

class AvatarHelper(
    private val resourceHelper: IResourceHelper
): IAvatarHelper {

    override fun getAvatarDrawable(avatarId: Int): Int = getAvatarById(avatarId)

    private fun getAvatarById(avatarId: Int) : Int {
        return when(avatarId) {
            1 -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.First)
            else -> resourceHelper.extractDrawableResource(DrawableResource.Avatar.Default)
        }
    }
}