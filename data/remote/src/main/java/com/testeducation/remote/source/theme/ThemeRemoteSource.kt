package com.testeducation.remote.source.theme

import com.testeducation.core.source.remote.theme.IThemeRemoteSource
import com.testeducation.domain.model.global.OrderSide
import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.remote.client.retrofit.category.ThemeRetrofitClient
import com.testeducation.remote.converter.global.toRemote
import com.testeducation.remote.converter.test.toModels
import com.testeducation.remote.converter.test.toRemote
import com.testeducation.remote.utils.getResult

class ThemeRemoteSource(
    private val themeRetrofitClient: ThemeRetrofitClient
) : IThemeRemoteSource {

    override suspend fun getThemes(
        query: String,
        orderField: ThemeOrderField,
        direction: OrderSide
    ): List<ThemeShort> {
        val remoteField = orderField.toRemote()
        val remoteDirection = direction.toRemote()
        return themeRetrofitClient.getThemes(query, remoteField, remoteDirection)
            .getResult().data.toModels()
    }
}
