package com.testeducation.domain.repository.theme

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.model.theme.ThemeShort
import kotlinx.coroutines.flow.Flow

interface IThemeRepository {

    suspend fun getThemesReactive(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderDirection
    ): Flow<List<ThemeShort>>
}
