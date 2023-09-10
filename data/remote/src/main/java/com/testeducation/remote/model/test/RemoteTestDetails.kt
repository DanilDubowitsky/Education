package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName
import com.testeducation.remote.client.remote.question.QuestionRemoteClient
import com.testeducation.remote.model.question.RemoteQuestion
import com.testeducation.remote.request.question.AnswerCreateRequest

data class RemoteTestDetails(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("style")
    val style: RemoteTestStyle,
    @SerializedName("settings")
    val settings: RemoteTestSettings,
    @SerializedName("question")
    val question: List<RemoteQuestion>,
    @SerializedName("status")
    val status: String,
    @SerializedName("likes")
    val likes: Long,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("passes")
    val passesUser: Long,
    @SerializedName("passed")
    val passed: Boolean,
    @SerializedName("theme")
    val theme: RemoteThemeShort
)