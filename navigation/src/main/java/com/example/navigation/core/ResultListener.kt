package com.example.navigation.core

fun interface ResultListener<T> {
    fun onResult(data: T)
}
