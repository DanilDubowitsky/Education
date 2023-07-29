package com.testeducation.local.source.theme

import com.testeducation.core.source.local.theme.IThemeLocalSource
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.local.converter.theme.toEntities
import com.testeducation.local.converter.theme.toModels
import com.testeducation.local.dao.theme.ThemeShortDao
import com.testeducation.local.entity.theme.ThemeShortEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeLocalSource(
    private val themeShortDao: ThemeShortDao
) : IThemeLocalSource {

    override suspend fun addShortThemes(themes: List<ThemeShort>) =
        themeShortDao.insertOrUpdateShortThemes(themes.toEntities())

    override suspend fun getShortThemesReactive(): Flow<List<ThemeShort>> =
        themeShortDao.getThemesShortReactive().map(List<ThemeShortEntity>::toModels)

    override suspend fun hasEntries(): Boolean =
        themeShortDao.hasEntries()

    override suspend fun clearTableAndAddThemes(themes: List<ThemeShort>) =
        themeShortDao.clearTableAndSetData(themes.toEntities())
}
