package com.testeducation.screen.tests.creation

import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.utils.MainColor

data class TestCreationModelState(
    val themes: List<ThemeShort> = emptyList(),
    val iconDesign: List<IconDesignItem> = emptyList(),
    val loadingState: LoadingState = LoadingState.LOADING,
    val stepState: StepState = StepState.FIRST,
    val colorState: ColorState = ColorState.GREEN,
    val title: String = "",
    val selectedTheme: ThemeShort = ThemeShort("", ""),
    val styleCurrent: CardTestStyle = CardTestStyle.X,
    val backBtnText: String = "",
    val nextBtnText: String = "",
) {
    enum class LoadingState {
        LOADING,
        IDLE
    }

    enum class StepState {
        FIRST, SECOND;

        companion object {
            fun StepState.isFirst() = this == FIRST
        }
    }

    enum class ColorState(val color: String) {
        GREEN(MainColor.colorGreen),
        BLUE(MainColor.colorBlue),
        RED(MainColor.colorRed),
        ORANGE(MainColor.colorOrange)
    }
}