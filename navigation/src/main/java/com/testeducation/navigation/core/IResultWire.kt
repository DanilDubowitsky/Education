package com.testeducation.navigation.core

interface IResultWire {

    fun <T> setResultListener(
        key: ResultKey<T>,
        listener: ResultListener<T>,
    ): Disposable

    fun <T> sendResult(key: ResultKey<T>, data: T, needRemoveListener: Boolean = true)
}
