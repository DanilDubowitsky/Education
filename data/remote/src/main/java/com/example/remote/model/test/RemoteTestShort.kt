package com.example.remote.model.test

import com.google.gson.annotations.SerializedName

data class RemoteTestShort(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("questions_count")
    val questionsCount: Int,
    @SerializedName("is_public")
    val isPublic: Boolean,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("passes_count")
    val passesCount: Int,
    @SerializedName("theme")
    val theme: RemoteThemeShort
)
