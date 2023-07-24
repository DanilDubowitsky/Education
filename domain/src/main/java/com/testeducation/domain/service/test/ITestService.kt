package com.testeducation.domain.service.test

interface ITestService {

    suspend fun toggleTestLike(id: String, liked: Boolean)

}
