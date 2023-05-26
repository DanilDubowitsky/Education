package com.example.remote.converter.global

import com.example.domain.model.global.OrderSide

private const val ASCENDING = "asc"
private const val DESCENDING = "desc"

fun OrderSide.toRemote() = when (this) {
    OrderSide.ASCENDING -> ASCENDING
    OrderSide.DESCENDING -> DESCENDING
}
