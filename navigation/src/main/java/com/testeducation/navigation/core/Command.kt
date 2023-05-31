package com.testeducation.navigation.core

import com.testeducation.navigation.screen.NavigationScreen

sealed interface Command {
    data class Forward(val screen: NavigationScreen) : Command
    data class Replace(val screen: NavigationScreen) : Command
    object Back : Command
}