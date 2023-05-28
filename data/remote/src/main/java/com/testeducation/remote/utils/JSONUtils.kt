package com.testeducation.remote.utils

import com.google.gson.Gson

object JSONUtils {

    inline fun <reified T> toJsonObject(stringJson: String): T {
        return Gson().fromJson(stringJson, T::class.java)
    }

}
