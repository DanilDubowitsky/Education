package com.example.core.source.remote.theme

import com.example.domain.model.global.OrderSide
import com.example.domain.model.theme.ThemeOrderField
import com.example.domain.model.theme.ThemeShort

interface IThemeRemoteSource {

    suspend fun getThemes(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderSide
    ): List<ThemeShort>

}
