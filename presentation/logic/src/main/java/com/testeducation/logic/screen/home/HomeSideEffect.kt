package com.testeducation.logic.screen.home

sealed interface HomeSideEffect {

    object SlideDownNavigation : HomeSideEffect

    object SlideUpNavigation : HomeSideEffect

}
