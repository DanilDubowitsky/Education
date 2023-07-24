package com.testeducation.core.repository.theme

import com.testeducation.core.source.local.theme.IThemeLocalSource
import com.testeducation.core.source.remote.theme.IThemeRemoteSource
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.domain.repository.theme.IThemeRepository
import kotlinx.coroutines.flow.Flow

class ThemeRepository(
    private val themeRemoteSource: IThemeRemoteSource,
    private val themeLocalSource: IThemeLocalSource
) : IThemeRepository {

    override suspend fun getThemesReactive(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderDirection
    ): Flow<List<ThemeShort>> {
        val themes = themeRemoteSource.getThemes(
            query,
            orderField,
            direction
        )
        themeLocalSource.addShortThemes(themes)
        return themeLocalSource.getShortThemesReactive()
    }
}
