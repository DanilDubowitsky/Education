package com.testeducation.local.converter

inline fun <reified T : Enum<T>> String.toEnumModel() = java.lang.Enum.valueOf(T::class.java, this)

inline fun <reified T : Enum<T>> Enum<T>.toEnumEntity() = this.name
