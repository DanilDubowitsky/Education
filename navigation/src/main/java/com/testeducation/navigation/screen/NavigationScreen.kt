package com.testeducation.navigation.screen

import com.testeducation.logic.model.auth.ConfirmationType
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.navigation.core.ResultKey
import java.io.Serializable

sealed interface NavigationScreen : Serializable {

    sealed interface Auth : NavigationScreen {

        object Login : Auth

        object Registration : Auth

        data class CodeConfirmation(
            val email: String,
            val confirmationType: ConfirmationType
        ) : Auth

        object PasswordResetEmail : Auth

        data class NewPassword(
            val email: String,
            val token: String
        ) : Auth

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

        object CreationTest : Main {

            object OnCreationTestResult : ResultKey<String>
        }

        data class SelectionTest(val idTest: String = "") : Main

        object Tests : Main {

            object OnScrollToBottom : ResultKey<Unit>

            object OnScrollToTop : ResultKey<Unit>
        }

        object LikedTests : Main

        object Library : Main
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

        data class Library(
            val getTypeUI: TestGetTypeUI
        ) : Tests

        data class Details(
            val testId: String
        ) : Tests

        data class Preview(
            val id: String
        ) : Tests
    }

    sealed interface Questions : NavigationScreen {

        data class QuestionsPreview(
            val testId: String
        ) : Questions

        data class QuestionEditor(
            val questionTypeUiItem: QuestionTypeUiItem,
            val testId: String
        ) : Questions

        data class TimeQuestion(val time: Long) : Questions

        // TODO: move to class
        object OnSelectionQuestionTypeChanged : ResultKey<QuestionTypeUiItem>

        // TODO: move to class
        object OnTimeQuestionChanged : ResultKey<Long>

    }

}
