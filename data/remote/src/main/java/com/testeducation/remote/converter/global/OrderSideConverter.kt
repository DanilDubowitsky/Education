package com.testeducation.remote.converter.global

import com.testeducation.domain.model.global.OrderDirection

private const val ASCENDING = "asc"
private const val DESCENDING = "desc"

fun OrderDirection.toRemote() = when (this) {
    OrderDirection.ASCENDING -> ASCENDING
    OrderDirection.DESCENDING -> DESCENDING
}
