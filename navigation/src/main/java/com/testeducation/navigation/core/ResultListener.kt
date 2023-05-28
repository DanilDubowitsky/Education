package com.testeducation.navigation.core

fun interface ResultListener<T> {
    fun onResult(data: T)
}
