package com.example.utils

object StringUtils {
    fun String.isValid() = this.isNotEmpty() && this.isNotBlank()

    fun String.isNotValid() = !this.isValid()
}