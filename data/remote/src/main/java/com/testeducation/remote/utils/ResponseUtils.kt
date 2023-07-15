package com.testeducation.remote.utils

import com.testeducation.domain.exception.ServerException
import com.testeducation.remote.model.global.GenericResponse
import retrofit2.Response


fun <T> Response<T>.getResult(): T {
    if (this.isSuccessful) return this.body()!!
    val errorBodyString = this.errorBody()?.string().orEmpty()
    val errorJson = JSONUtils.toJsonObject<GenericResponse<T>>(errorBodyString)
    val message = errorJson.status?.message.takeIf {
        !it.isNullOrEmpty()
    } ?: errorBodyString
    throw ServerException(message)
}

fun <T : GenericResponse<Unit>> Response<T>.getResult() {
    if (this.isSuccessful) return
    val errorBodyString = this.errorBody()?.string().orEmpty()
    val errorJson = JSONUtils.toJsonObject<GenericResponse<T>>(errorBodyString)
    val message = errorJson.status?.message.takeIf {
        !it.isNullOrEmpty()
    } ?: errorBodyString
    throw ServerException(message)
}
