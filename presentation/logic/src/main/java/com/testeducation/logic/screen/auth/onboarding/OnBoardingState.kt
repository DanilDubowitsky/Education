package com.testeducation.logic.screen.auth.onboarding

import com.testeducation.logic.model.auth.OnBoardingItemUi

data class OnBoardingState(
    val position: Int,
    val list: List<OnBoardingItemUi>
)