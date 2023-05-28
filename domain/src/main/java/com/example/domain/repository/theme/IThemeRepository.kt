package com.example.domain.repository.theme

import com.example.domain.model.global.OrderDirection
import com.example.domain.model.theme.ThemeOrderField
import com.example.domain.model.theme.ThemeShort

interface IThemeRepository {

    suspend fun getThemes(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderDirection
    ): List<ThemeShort>
}
