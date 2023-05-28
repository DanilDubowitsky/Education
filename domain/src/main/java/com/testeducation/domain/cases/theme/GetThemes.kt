package com.testeducation.domain.cases.theme

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.repository.theme.IThemeRepository

class GetThemes(
    private val themeRepository: IThemeRepository
) {

    suspend operator fun invoke(
        query: String = "",
        orderField: ThemeOrderField = ThemeOrderField.TITLE,
        direction: OrderDirection = OrderDirection.ASCENDING
    ) = themeRepository.getThemes(query, orderField, direction)
}
