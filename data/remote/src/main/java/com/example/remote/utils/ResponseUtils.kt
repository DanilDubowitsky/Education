package com.example.remote.utils

import com.example.domain.exception.ServerException
import com.example.remote.model.global.GenericResponse
import retrofit2.Response

object ResponseUtils {

    fun <T> Response<T>.getResult(): T {
        if (this.isSuccessful) return this.body()!!
        val errorBodyString = this.errorBody()?.string().orEmpty()
        val errorJson = JSONUtils.toJsonObject<GenericResponse<T>>(errorBodyString)
        throw ServerException(errorJson.status.message)
    }

    fun <T : GenericResponse<Unit>> Response<T>.getResult() {
        if (this.isSuccessful) return
        val errorBodyString = this.errorBody()?.string().orEmpty()
        val errorJson = JSONUtils.toJsonObject<GenericResponse<T>>(errorBodyString)
        throw ServerException(errorJson.status.message)
    }

}
