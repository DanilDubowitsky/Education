package com.testeducation.navigation.screen

import com.testeducation.logic.model.auth.ConfirmationType
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.model.test.TestLibraryGetTypeUI
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.navigation.core.ResultKey
import java.io.Serializable

sealed interface NavigationScreen : Serializable {

    sealed interface Auth : NavigationScreen {

        object Login : Auth

        object Registration : Auth

        data class CodeConfirmation(
            val email: String,
            val token: String,
            val confirmationType: ConfirmationType
        ) : Auth

        object PasswordResetEmail : Auth

        data class NewPassword(
            val email: String,
            val code: String
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

        data class WebView(val url: String): Common
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

        object Profile : Main

        object Library : Main

        object HomeLibrary : Main
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
            val getTypeUI: TestLibraryGetTypeUI
        ) : Tests

        data class Details(
            val testId: String
        ) : Tests

        data class Settings(
            val testId: String,
            val titleTest: String,
            val colorTest: String,
            val imageTest: String,
            val idTheme: String,
            val themeName: String,
            val countQuestion: Int,
        ) : Tests {
            object OnTestSettingsResult : ResultKey<Unit>
        }

        data class Preview(
            val id: String?,
            val code: String? = null
        ) : Tests

        data class Passing(
            val id: String
        ) : Tests

        data class TestStyleChangerData(
            val testId: String,
            val testName: String,
            val themeName: String,
            val color: String,
            val background: String
        ) : Tests {
            object OnTestStyleChanger : ResultKey<OnTestStyleChanger.TestStyleChangerResult> {

                data class TestStyleChangerResult(
                    val color: String,
                    val styleCurrent: CardTestStyle
                )
            }
        }

        data class Result(
            val correctAnswers: Int,
            val incorrectAnswers: Int,
            val isSuccess: Boolean
        ) : Tests {

            object OpenMainPage : ResultKey<Unit>

            object OpenResults : ResultKey<Unit>
        }

        data class FailedResult(
            val isCheating: Boolean
        ) : Tests

        data class Statistic(
            val testId: String
        ) : Tests

        data class ShareCode(
            val testId: String
        ) : Tests

        object EnterCode : Tests

        data class FullAnswer(
            val text: String
        ) : Tests

        data class TestSort(
            val orderField: String,
            val direction: String
        ) : Tests {

            object OnSortChanged : ResultKey<SortValues>

            data class SortValues(
                val orderField: String,
                val direction: String
            )
        }

        data class Action(
            val testId: String,
            val testTitle: String
        ) : Tests
    }

    sealed interface Questions : NavigationScreen {

        data class QuestionsPreview(
            val testId: String

        ) : Questions

        data class QuestionEditor(
            val questionTypeUiItem: QuestionTypeUiItem,
            val testId: String,
            val orderQuestion: Int = 1,
            val questionId: String = ""
        ) : Questions

        data class TimeQuestion(val time: Long) : Questions

        // TODO: move to class
        object OnSelectionQuestionTypeChanged : ResultKey<QuestionTypeUiItem>

        // TODO: move to class
        object OnTimeQuestionChanged : ResultKey<Long>

        data class AnswerInput(val answerText: String, val color: Int, val firstAnswer: Boolean) :
            Questions {
            object OnAnswerInput : ResultKey<OnAnswerInput.Result> {
                data class Result(
                    val answerText: String,
                    val firstAnswer: Boolean
                )
            }
        }
    }

    sealed interface Profile : NavigationScreen {
        object Editor : Profile
        object Support : Profile
        data class Avatar(val avatarId: Int) : Profile {
            object OnAvatarUpdated: ResultKey<Int>
        }
    }

}
