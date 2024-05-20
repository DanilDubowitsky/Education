package com.testeducation.logic.screen.tests.edit

import java.util.Calendar

sealed interface TestPublishSideEffect {
    data class OpenDatePicker(val calendar: Calendar) : TestPublishSideEffect
    data class OpenTimePicker(val calendar: Calendar) : TestPublishSideEffect
}