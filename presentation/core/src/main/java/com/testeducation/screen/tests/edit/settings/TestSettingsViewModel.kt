package com.testeducation.screen.tests.edit.settings

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestSettings
import com.testeducation.domain.cases.test.UpdateTestSettings
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.model.test.TestAvailability
import com.testeducation.domain.model.test.TestQuestionOrder
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
    private val updateTestSettings: UpdateTestSettings,
    private val getThemes: GetThemes
) : BaseViewModel<TestSettingsModelState, TestSettingsState, TestSettingsSideEffect>(
    reducer,
    exceptionHandler
) {

    companion object {
        private const val TITLE_TEST_NAME_POSITION = 0
        private const val AVAILABILITY_ID = 1
        private const val ORDER_ID = 2
        private const val PREVIEW_SELECTABLE_ID = 3
        private const val ANTI_CHEAT_SELECTABLE_ID = 4
    }

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
                    ),
                    originalTestSettings = response
                )
            }
        }
    }

    fun saveSettings() = intent {
        val modelState = getModelState()
        val settingsList = modelState.testElementList
        val original = modelState.originalTestSettings
        var testSettingsItem = TestSettingsItem()
        settingsList.forEach { itemSettings ->
            when (itemSettings) {
                is TestSettingsElement.Choice -> {
                    when (itemSettings.id) {
                        AVAILABILITY_ID -> {
                            val availability = if (itemSettings.itemFirst.isSelected) {
                                itemSettings.itemFirst.value
                            } else {
                                itemSettings.itemSecond.value
                            }
                            val testAvailability = TestAvailability.valueOf(availability)
                            if (testAvailability != original.availability) {
                                testSettingsItem = testSettingsItem.copy(
                                    availability = testAvailability
                                )
                            }
                        }

                        else -> {
                            val order = if (itemSettings.itemFirst.isSelected) {
                                itemSettings.itemFirst.value
                            } else {
                                itemSettings.itemSecond.value
                            }
                            val testOrder = TestQuestionOrder.valueOf(order)
                            if (testOrder != original.testQuestionOrder) {
                                testSettingsItem = testSettingsItem.copy(
                                    testQuestionOrder = testOrder
                                )
                            }

                        }
                    }
                }

                is TestSettingsElement.Selectable -> {
                    testSettingsItem = when (itemSettings.id) {
                        PREVIEW_SELECTABLE_ID -> {
                            testSettingsItem.copy(
                                allowPreviewQuestions = itemSettings.isSelected
                            )
                        }

                        else -> {
                            testSettingsItem.copy(
                                antiCheating = itemSettings.isSelected
                            )
                        }
                    }
                }

                else -> {

                }
            }
        }
        updateTestSettings(testId, testSettingsItem)
    }

    fun updateTextInput(currentId: Int, newText: String) = intent {
        val modelState = getModelState()
        val list = modelState.testElementList
        val textInput = list.find { it.id == currentId }
        if (textInput is TestSettingsElement.TestInput) {
            val newList = list.map {
                if (textInput == it) {
                    textInput.copy(
                        valueInput = newText
                    )
                } else textInput
            }
            updateModelState {
                copy(
                    testElementList = newList,
                    changedIdList = modelState.changedIdList.plus(currentId)
                )
            }
        }
    }

    fun updateChoice(currentId: Int, position: Int) = intent {
        val modelState = getModelState()
        val list = modelState.testElementList
        val choiceItem = list.find { it.id == currentId }
        if (choiceItem is TestSettingsElement.Choice) {
            val newList = list.map {
                if (choiceItem == it) {
                    choiceItem.copy(
                        itemFirst = choiceItem.itemFirst.copy(
                            isSelected = position == TestSettingsElement.Choice.Position.FIRST
                        ),
                        itemSecond = choiceItem.itemSecond.copy(
                            isSelected = position == TestSettingsElement.Choice.Position.SECOND
                        )
                    )
                } else it
            }
            updateModelState {
                copy(
                    testElementList = newList,
                    changedIdList = modelState.changedIdList.plus(currentId)
                )
            }
        }
    }

    fun updateSelectable(currentId: Int) = intent {
        val modelState = getModelState()
        val list = modelState.testElementList
        val selectableItem = list.find { it.id == currentId }
        if (selectableItem is TestSettingsElement.Selectable) {
            val newList = list.map {
                if (selectableItem == it) {
                    selectableItem.copy(
                        isSelected = !selectableItem.isSelected
                    )
                } else it
            }
            updateModelState {
                copy(
                    testElementList = newList,
                    changedIdList = modelState.changedIdList.plus(currentId)
                )
            }
        }
    }

    private fun prepareTestElement(
        testSettingsItem: TestSettingsItem,
        listTheme: List<ThemeShort>
    ): List<TestSettingsElement> {
        var currentId = 1000
        val textInputSettings = TestSettingsElement.TestInput(
            id = TITLE_TEST_NAME_POSITION,
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
        val choice = TestSettingsElement.Choice(
            id = AVAILABILITY_ID,
            title = "Доступ к тесту",
            itemFirst = TestSettingsElement.Choice.Item(
                title = "Всем",
                value = TestAvailability.Public.toString(),
                isSelected = true
            ),
            itemSecond = TestSettingsElement.Choice.Item(
                title = "По всем",
                value = TestAvailability.Private.toString(),
                isSelected = false
            )
        )
        val choiceOrder = TestSettingsElement.Choice(
            id = ORDER_ID,
            title = "Порядок вопросов",
            itemFirst = TestSettingsElement.Choice.Item(
                title = "Последовательно",
                value = TestQuestionOrder.Sequencial.toString(),
                isSelected = true
            ),
            itemSecond = TestSettingsElement.Choice.Item(
                title = "Перемешать",
                value = TestQuestionOrder.Shuffled.toString(),
                isSelected = false
            )
        )
        currentId++
        val textInputMinAnswer = TestSettingsElement.TestInput(
            id = currentId,
            title = "Минимум правильных ответов для успешного прохождения теста",
            valueInput = testSettingsItem.minCorrectAnswer.toString()
        )
        val selectableShow = TestSettingsElement.Selectable(
            id = PREVIEW_SELECTABLE_ID,
            title = "Предварительный просмотр вопросов",
            description = "",
            isSelected = testSettingsItem.allowPreviewQuestions ?: false
        )
        val selectableAntiCheat = TestSettingsElement.Selectable(
            id = ANTI_CHEAT_SELECTABLE_ID,
            title = "Антисписывание",
            description = "",
            isSelected = testSettingsItem.antiCheating ?: false
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