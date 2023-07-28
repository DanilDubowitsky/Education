package com.testeducation.logic.screen.tests.creation

import com.testeducation.logic.model.theme.ThemeShortUI

sealed interface TestCreationSideEffect {
    data class CreateChip(val themes: List<ThemeShortUI>) : TestCreationSideEffect
    data class TitleInputError(val error: String) : TestCreationSideEffect
}