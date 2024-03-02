package com.testeducation.remote.model.test

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
    @SerializedName("passes")
    val passesCount: Int,
    @SerializedName("theme")
    val theme: RemoteThemeShort,
    @SerializedName("style")
    val style: RemoteTestStyle,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("passed")
    val passed: Boolean
)
