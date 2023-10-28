package com.testeducation.screen.tests.edit.style

import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.utils.MainColor

data class TestStyleChangerModelState(
    val iconDesign: List<IconDesignItem> = emptyList(),
    val colorState: ColorState = ColorState.GREEN,
    val styleCurrent: CardTestStyle = CardTestStyle.X,
    val title: String = "",
    val selectedTheme: ThemeShort = ThemeShort("", ""),
) {
    enum class ColorState(val color: String) {
        GREEN(MainColor.colorGreen),
        BLUE(MainColor.colorBlue),
        RED(MainColor.colorRed),
        ORANGE(MainColor.colorOrange);

        companion object {
            fun getColor(colorParams: String) = when(colorParams) {
                MainColor.colorGreen -> GREEN
                MainColor.colorBlue -> BLUE
                MainColor.colorRed -> RED
                MainColor.colorOrange -> ORANGE
                else -> RED
            }
        }
    }
}