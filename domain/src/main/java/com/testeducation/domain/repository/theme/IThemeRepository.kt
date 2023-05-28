package com.testeducation.domain.repository.theme

import com.testeducation.domain.model.global.OrderSide
import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.model.theme.ThemeShort

interface IThemeRepository {

    suspend fun getThemes(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderSide
    ): List<ThemeShort>
}
