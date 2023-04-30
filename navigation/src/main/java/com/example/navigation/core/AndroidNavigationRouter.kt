package com.example.navigation.core

import com.example.navigation.screen.NavigationScreen

class AndroidNavigationRouter(
    private val navigationHost: NavigationHost
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
}
