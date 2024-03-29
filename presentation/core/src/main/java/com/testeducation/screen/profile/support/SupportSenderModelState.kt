package com.testeducation.screen.profile.support

import com.testeducation.domain.model.profile.CategorySupport

data class SupportSenderModelState(
    val text: String = "",
    val categorySupport: CategorySupport = CategorySupport.Message,
    val isLoading: Boolean = false
)