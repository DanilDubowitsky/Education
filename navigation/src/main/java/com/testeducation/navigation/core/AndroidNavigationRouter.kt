package com.testeducation.navigation.core

import com.testeducation.navigation.screen.NavigationScreen

class AndroidNavigationRouter(
    private val navigationHost: NavigationHost,
    private val resultWire: IResultWire
) : NavigationRouter {

    override fun navigateTo(screen: NavigationScreen, key: String?) {
        navigationHost.executeCommand(Command.Forward(screen), key)
    }

    override fun exit(key: String?) {
        navigationHost.executeCommand(Command.Back, key)
    }

    override fun replace(screen: NavigationScreen, key: String?) {
        navigationHost.executeCommand(Command.Replace(screen), key)
    }

    override fun <T> setResultListener(key: ResultKey<T>, listener: ResultListener<T>): Disposable {
        return resultWire.setResultListener(key, listener)
    }

    override fun <T> sendResult(key: ResultKey<T>, data: T) {
        resultWire.sendResult(key, data)
    }
}
