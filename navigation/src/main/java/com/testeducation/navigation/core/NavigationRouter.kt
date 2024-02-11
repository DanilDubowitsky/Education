package com.testeducation.navigation.core

import com.testeducation.navigation.screen.NavigationScreen

interface NavigationRouter : IResultWire {
    fun navigateTo(screen: NavigationScreen, addToBackStack: Boolean = true, key: String? = null)
    fun exit(key: String? = null)
    fun replace(screen: NavigationScreen, key: String? = null)
    fun newRootChain(screen: NavigationScreen, key: String? = null)
    fun homeNavigateTo(screen: NavigationScreen, key: String? = null)
}
