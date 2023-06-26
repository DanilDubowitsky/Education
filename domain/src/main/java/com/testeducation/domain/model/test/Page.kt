package com.testeducation.domain.model.test

data class Page<T>(
    val pageIndex: Int,
    val pageSize: Int,
    val pageTotal: Int,
    val itemsTotal: Int,
    val tests: List<T>
)
