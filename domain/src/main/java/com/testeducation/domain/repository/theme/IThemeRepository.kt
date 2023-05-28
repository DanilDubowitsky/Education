package com.testeducation.domain.repository.theme

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.model.theme.ThemeShort

interface IThemeRepository {

    suspend fun getThemes(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderDirection
    ): List<ThemeShort>
}
