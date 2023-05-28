package com.testeducation.domain.cases.theme

import com.testeducation.domain.model.global.OrderSide
import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.repository.theme.IThemeRepository

class GetThemes(
    private val themeRepository: IThemeRepository
) {

    suspend operator fun invoke(
        query: String = "",
        orderField: ThemeOrderField = ThemeOrderField.TITLE,
        direction: OrderSide = OrderSide.ASCENDING
    ) = themeRepository.getThemes(query, orderField, direction)
}
