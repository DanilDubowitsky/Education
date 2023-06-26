package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName

data class RemotePage<T>(
    @SerializedName("page_index")
    val pageIndex: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    @SerializedName("page_total")
    val pageTotal: Int,
    @SerializedName("items_total")
    val itemsTotal: Int,
    @SerializedName("content")
    val tests: List<T>
)
