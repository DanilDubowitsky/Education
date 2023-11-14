package com.testeducation.remote.model.user

import com.google.gson.annotations.SerializedName

data class RemoteUserStatistics(
    @SerializedName("created_tests")
    val createdTestCount: Int? = null,
    @SerializedName("passed_answers")
    val passedTestCount: Int? = null,
)