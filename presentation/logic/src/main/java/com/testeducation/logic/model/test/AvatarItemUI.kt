package com.testeducation.logic.model.test

import androidx.annotation.DrawableRes

data class AvatarItemUI(
    val id: Int,
    @DrawableRes val avatarDrawableId: Int,
    val isSelected: Boolean,
)