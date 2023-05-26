package com.example.domain.cases.theme

import com.example.domain.model.global.OrderSide
import com.example.domain.model.theme.ThemeOrderField
import com.example.domain.repository.theme.IThemeRepository

class GetThemes(
    private val themeRepository: IThemeRepository
) {

    suspend operator fun invoke(
        query: String = "",
        orderField: ThemeOrderField = ThemeOrderField.TITLE,
        direction: OrderSide = OrderSide.ASCENDING
    ) = themeRepository.getThemes(query, orderField, direction)
}
