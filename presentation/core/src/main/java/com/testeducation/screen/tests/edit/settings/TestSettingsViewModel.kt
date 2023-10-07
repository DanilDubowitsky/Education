package com.testeducation.screen.tests.edit.settings

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestSettings
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.model.test.TestSettingsElement
import com.testeducation.domain.model.test.TestSettingsItem
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.settings.TestSettingsSideEffect
import com.testeducation.logic.screen.tests.settings.TestSettingsState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class TestSettingsViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestSettingsModelState, TestSettingsState>,
    private val resourceHelper: IResourceHelper,
    private val testId: String,
    private val titleTest: String,
    private val colorTest: String,
    private val imageTest: String,
    private val idTheme: String,
    private val router: NavigationRouter,
    private val getTestSettings: GetTestSettings,
    private val getThemes: GetThemes
) : BaseViewModel<TestSettingsModelState, TestSettingsState, TestSettingsSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestSettingsModelState = TestSettingsModelState()

    init {
        intent {
            val response = getTestSettings(
                testId
            )
            updateModelState {
                copy(
                    testElementList = prepareTestElement(
                        response, listOf()
                    )
                )
            }
        }
    }

    private fun prepareTestElement(
        testSettingsItem: TestSettingsItem,
        listTheme: List<ThemeShort>
    ): List<TestSettingsElement> {
        var currentId = 1
        currentId++
        val textInputSettings = TestSettingsElement.TestInput(
            id = currentId,
            title = "Название теста",
            valueInput = titleTest
        )
        currentId++
        val designTest = TestSettingsElement.Design(
            id = currentId,
            title = "Обложка",
            color = colorTest,
            image = imageTest
        )
        currentId++
        val horizontalScroll = TestSettingsElement.HorizontalScroll(
            id = currentId,
            title = "Тема",
            list = listTheme.getHorizontalScrollThemes()
        )
        currentId++
        val choice = TestSettingsElement.Choice(
            id = currentId,
            title = "Доступ к тесту",
            itemFirst = TestSettingsElement.Choice.Item(
                title = "Всем",
                isSelected = true
            ),
            itemSecond = TestSettingsElement.Choice.Item(
                title = "По всем",
                isSelected = false
            )
        )
        currentId++
        val choiceOrder = TestSettingsElement.Choice(
            id = currentId,
            title = "Порядок вопросов",
            itemFirst = TestSettingsElement.Choice.Item(
                title = "Последовательно",
                isSelected = true
            ),
            itemSecond = TestSettingsElement.Choice.Item(
                title = "Перемешать",
                isSelected = false
            )
        )
        currentId++
        val textInputMinAnswer = TestSettingsElement.TestInput(
            id = currentId,
            title = "Минимум правильных ответов для успешного прохождения теста",
            valueInput = testSettingsItem.minCorrectAnswer.toString()
        )
        currentId++
        val selectableShow = TestSettingsElement.Selectable(
            id = currentId,
            title = "Предварительный просмотр вопросов",
            description = "",
            isSelected = testSettingsItem.allowPreviewQuestions
        )
        currentId++
        val selectableAntiCheat = TestSettingsElement.Selectable(
            id = currentId,
            title = "Антисписывание",
            description = "",
            isSelected = testSettingsItem.antiCheating
        )
        return listOf(
            textInputSettings,
            designTest,
            horizontalScroll,
            choice,
            choiceOrder,
            textInputMinAnswer,
            selectableShow,
            selectableAntiCheat
        )
    }

    private fun List<ThemeShort>.getHorizontalScrollThemes() = map { item ->
        TestSettingsElement.HorizontalScroll.Item(
            id = item.id,
            title = item.title,
            isSelected = idTheme == item.id
        )
    }
}