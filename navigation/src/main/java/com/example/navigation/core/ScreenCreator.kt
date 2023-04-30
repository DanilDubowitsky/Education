package com.example.navigation.core

fun interface ScreenCreator<A, R> {
    fun create(argument: A): R
}
