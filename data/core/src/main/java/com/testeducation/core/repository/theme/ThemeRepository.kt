package com.testeducation.core.repository.theme

import com.testeducation.core.source.remote.theme.IThemeRemoteSource
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.domain.repository.theme.IThemeRepository

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
