package com.testeducation.navigation.core

import com.testeducation.navigation.screen.NavigationScreen

interface NavigationRouter : IResultWire {
    fun navigateTo(screen: NavigationScreen, key: String? = null)
    fun exit(key: String? = null)
    fun replace(screen: NavigationScreen, key: String? = null)
    fun newRootChain(screen: NavigationScreen, key: String? = null)
}
