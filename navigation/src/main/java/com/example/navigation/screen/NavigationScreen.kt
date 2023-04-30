package com.example.navigation.screen

sealed interface NavigationScreen {

    sealed interface Auth : NavigationScreen {

        object Login : Auth

        object Registration : Auth

    }

}
