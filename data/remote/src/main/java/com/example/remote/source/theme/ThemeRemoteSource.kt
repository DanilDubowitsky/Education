package com.example.remote.source.theme

import com.example.core.source.remote.theme.IThemeRemoteSource
import com.example.domain.model.global.OrderDirection
import com.example.domain.model.theme.ThemeOrderField
import com.example.domain.model.theme.ThemeShort
import com.example.remote.client.retrofit.category.ThemeRetrofitClient
import com.example.remote.converter.global.toRemote
import com.example.remote.converter.test.toModels
import com.example.remote.converter.test.toRemote
import com.example.remote.utils.ResponseUtils.getResult

class ThemeRemoteSource(
    private val themeRetrofitClient: ThemeRetrofitClient
) : IThemeRemoteSource {

    override suspend fun getThemes(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderDirection
    ): List<ThemeShort> {
        val remoteField = orderField.toRemote()
        val remoteDirection = direction.toRemote()
        return themeRetrofitClient.getThemes(query, remoteField, remoteDirection)
            .getResult().data.toModels()
    }
}
