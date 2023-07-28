package com.testeducation.navigation.screen

import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.model.test.TestShortUI
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

        data class PopUpInformation(
            val titleText: String,
        ) : Common {

            object OnConfirm : ResultKey<Unit>
        }
    }

    sealed interface Main : NavigationScreen {

        object Home : Main

        object CreationTest : Main

        object SelectionTest : Main

        object Tests : Main {

            object OnScrollToBottom : ResultKey<Unit>

            object OnScrollToTop : ResultKey<Unit>
        }

        object OnCreationTestResult : ResultKey<Boolean>
    }

    sealed interface Tests : NavigationScreen {

        data class Filters(
            val filters: TestFiltersUI
        ) : Tests {

            object OnFiltersChanged : ResultKey<TestFiltersUI> {

                data class TestFiltersResult(
                    val filters: TestFiltersUI,
                    val items: List<TestShortUI>
                )
            }
        }
    }

    sealed interface QuestionCreation : NavigationScreen {
        data class QuestionEditor(val questionTypeUiItem: QuestionTypeUiItem) : QuestionCreation
    }

}
