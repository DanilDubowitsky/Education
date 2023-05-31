package com.testeducation.navigation.screen

import com.testeducation.navigation.core.ResultKey
import java.io.Serializable

sealed interface NavigationScreen : Serializable {

    sealed interface Auth : NavigationScreen {

        object Login : Auth

        object Registration : Auth

        object EmailConfirmation : Auth

    }

    sealed interface Common : NavigationScreen {

        data class Confirmation(
            val confirmationText: String,
            val titleText: String? = null,
            val positiveBtnText: String? = null,
            val negativeBtnText: String? = null
        ) : Common {

            object OnConfirm : ResultKey<Unit>
        }

        data class Information(
            val description: String,
            val titleText: String? = null,
            val btnText: String? = null
        ) : Common
    }

    sealed interface Main : NavigationScreen {

        object Home : Main

        object Tests : Main

        object CreationTest : Main
    }

}
