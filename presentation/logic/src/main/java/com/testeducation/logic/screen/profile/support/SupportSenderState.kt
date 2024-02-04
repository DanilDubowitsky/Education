package com.testeducation.logic.screen.profile.support

data class SupportSenderState(
    val categorySelected: CategoryUi = CategoryUi.Message,
    val isLoading: Boolean
) {
   enum class CategoryUi {
       Bug, Message, Wish
   }
}