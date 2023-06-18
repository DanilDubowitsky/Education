package com.testeducation.navigation.core

import androidx.annotation.AnimRes

class AnimationSet(
    @AnimRes val enterAnim: Int,
    @AnimRes val exitAnim: Int,
    @AnimRes val popEnterAnim: Int,
    @AnimRes val popExitAnim: Int,
)
