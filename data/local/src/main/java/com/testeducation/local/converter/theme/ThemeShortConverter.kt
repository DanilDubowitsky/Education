package com.testeducation.local.converter.theme

import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.local.entity.theme.ThemeShortEntity

fun ThemeShort.toEntity() = ThemeShortEntity(
    id,
    title
)

fun ThemeShortEntity.toModel() = ThemeShort(id, title)

fun List<ThemeShortEntity>.toModels() = this.map(ThemeShortEntity::toModel)

fun List<ThemeShort>.toEntities() = this.map(ThemeShort::toEntity)
