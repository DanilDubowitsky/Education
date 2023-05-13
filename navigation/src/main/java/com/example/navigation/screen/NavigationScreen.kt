package com.example.navigation.screen

import com.example.navigation.core.ResultKey
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
    }

}
