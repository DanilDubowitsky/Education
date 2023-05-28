package com.example.core.repository.theme

import com.example.core.source.remote.theme.IThemeRemoteSource
import com.example.domain.model.global.OrderDirection
import com.example.domain.model.theme.ThemeOrderField
import com.example.domain.model.theme.ThemeShort
import com.example.domain.repository.theme.IThemeRepository

class ThemeRepository(
    private val themeRemoteSource: IThemeRemoteSource
) : IThemeRepository {

    override suspend fun getThemes(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderDirection
    ): List<ThemeShort> =
        themeRemoteSource.getThemes(
            query,
            orderField,
            direction
        )
}
