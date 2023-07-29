package com.testeducation.core.source.local.theme

import com.testeducation.domain.model.theme.ThemeShort
import kotlinx.coroutines.flow.Flow

interface IThemeLocalSource {

    suspend fun addShortThemes(themes: List<ThemeShort>)

    suspend fun getShortThemesReactive(): Flow<List<ThemeShort>>

    suspend fun hasEntries(): Boolean

    suspend fun clearTableAndAddThemes(
        themes: List<ThemeShort>
    )
}