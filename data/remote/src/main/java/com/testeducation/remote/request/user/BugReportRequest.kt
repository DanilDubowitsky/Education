package com.testeducation.remote.request.user

import com.google.gson.annotations.SerializedName

data class BugReportRequest(
    @SerializedName("category")
    val category: String,
    @SerializedName("user_comment")
    val comment: String
)
