package com.testeducation.logic.screen.common.confirm

sealed interface ConfirmSideEffect {
    class StartTime(val timeLeftMills: Long) : ConfirmSideEffect
}