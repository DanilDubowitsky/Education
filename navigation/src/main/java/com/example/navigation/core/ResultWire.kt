package com.example.navigation.core

import java.util.concurrent.ConcurrentHashMap

class ResultWire : IResultWire {

    private val listeners: ConcurrentHashMap<ResultKey<*>, ResultListener<*>> = ConcurrentHashMap()

    override fun <T> setResultListener(key: ResultKey<T>, listener: ResultListener<T>): Disposable {
        listeners[key] = listener
        return Disposable {
            listeners.remove(key)
        }
    }

    override fun <T> sendResult(key: ResultKey<T>, data: T) {
        val listener = listeners.remove(key) as? ResultListener<T> ?: return
        listener.onResult(data)
    }

}