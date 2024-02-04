package com.testeducation.logic.screen.profile.support

sealed interface SupportSenderSideEffect {
    object ClearFocus: SupportSenderSideEffect
}