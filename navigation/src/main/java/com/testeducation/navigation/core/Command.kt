package com.testeducation.navigation.core

import com.testeducation.navigation.screen.NavigationScreen

sealed interface Command {
    data class Forward(
        val screen: NavigationScreen,
        val addToBackStack: Boolean
    ) : Command
    data class Replace(val screen: NavigationScreen) : Command
    object Back : Command
    data class NewRootChain(val screen: NavigationScreen) : Command
}
