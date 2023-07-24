package com.testeducation.core.client.remote.test

interface ITestRemoteClient {

    suspend fun toggleTestLike(id: String, liked: Boolean)
}