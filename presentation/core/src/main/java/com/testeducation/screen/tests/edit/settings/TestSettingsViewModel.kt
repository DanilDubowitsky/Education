package com.testeducation.screen.tests.edit.settings

import androidx.lifecycle.viewModelScope
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
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.screen.tests.settings.TestSettingsSideEffect
import com.testeducation.logic.screen.tests.settings.TestSettingsState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getString
import kotlinx.coroutines.launch
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
    private val themeName: String,
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
        private const val TITLE_MIN_CURRENT_ANSWER_ID = 5
        private const val THEME_ID = 6
        private const val DESIGN_ID = 7
    }

    override val initialModelState: TestSettingsModelState = TestSettingsModelState()

    init {
        viewModelScope.launch {
            getThemes().collect { themes ->
                intent {
                    val modelState = getModelState()
                    if (modelState.testElementList.isEmpty()) {
                        val response = getTestSettings(
                            testId
                        )
                        updateModelState {
                            copy(
                                testElementList = prepareTestElement(
                                    response, themes
                                ),
                                originalTestSettings = response
                            )
                        }
                    } else {
                        updateModelState {
                            copy(
                                testElementList = prepareTestElement(
                                    modelState.originalTestSettings, themes
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun openTestStyleChanger() = intent {
        router.setResultListener(NavigationScreen.Tests.TestStyleChangerData.OnTestStyleChanger) { testStyle ->
            intent {
                val modelState = getModelState()
                val newDesign = modelState.testElementList.map {
                    if (it.id == DESIGN_ID && it is TestSettingsElement.Design) {
                        it.copy(
                            color = testStyle.color,
                            image = testStyle.styleCurrent.toString()
                        )
                    } else it
                }
                updateModelState {
                    copy(
                        testElementList = newDesign
                    )
                }
            }
        }
        router.navigateTo(
            NavigationScreen.Tests.TestStyleChangerData(
                testName = titleTest,
                themeName = themeName,
                testId = testId,
                color = colorTest,
                background = imageTest
            )
        )
    }

    fun exit() {
        router.exit()
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
                            if (itemSettings.isSelected != original.allowPreviewQuestions) {
                                testSettingsItem.copy(
                                    allowPreviewQuestions = itemSettings.isSelected
                                )
                            } else testSettingsItem
                        }

                        else -> {
                            if (itemSettings.isSelected != original.antiCheating) {
                                testSettingsItem.copy(
                                    antiCheating = itemSettings.isSelected
                                )
                            } else testSettingsItem
                        }
                    }
                }

                is TestSettingsElement.TestInput -> {
                    when (itemSettings.id) {
                        TITLE_TEST_NAME_POSITION -> {
                            if (itemSettings.valueInput != titleTest) {
                                testSettingsItem =
                                    testSettingsItem.copy(title = itemSettings.valueInput)
                            }
                        }

                        else -> {
                            if (itemSettings.valueInput != original.minCorrectAnswer.toString()) {
                                testSettingsItem =
                                    testSettingsItem.copy(minCorrectAnswer = itemSettings.valueInput.toInt())
                            }
                        }
                    }
                }

                is TestSettingsElement.HorizontalScroll -> {
                    if (itemSettings.id == THEME_ID) {
                        itemSettings.list.find { it.isSelected }?.let { theme ->
                            if (theme.id != idTheme) {
                                testSettingsItem =
                                    testSettingsItem.copy(themeId = theme.id)
                            }
                        }
                    }
                }

                else -> {

                }
            }
        }
        updateTestSettings(testId, testSettingsItem)
        router.sendResult(NavigationScreen.Tests.Settings.OnTestSettingsResult, Unit)
        router.exit()
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

    fun updateHorizontal(currentId: Int, elementId: String) = intent {
        val modelState = getModelState()
        val list = modelState.testElementList
        val selectableItem = list.find { it.id == currentId }
        if (selectableItem is TestSettingsElement.HorizontalScroll) {
            val newList = list.map {
                if (selectableItem == it) {
                    selectableItem.copy(
                        list = selectableItem.list.map { horizontalItem ->
                            horizontalItem.copy(
                                isSelected = elementId == horizontalItem.id
                            )
                        }.sortedBy { sort -> !sort.isSelected }
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
        val textInputSettings = TestSettingsElement.TestInput(
            id = TITLE_TEST_NAME_POSITION,
            title = StringResource.StringSettings.TestTitle.getString(resourceHelper),
            valueInput = titleTest,
            hint = StringResource.StringSettings.TestTitle.getString(resourceHelper)
        )
        val designTest = TestSettingsElement.Design(
            id = DESIGN_ID,
            title = StringResource.StringSettings.DesignTitle.getString(resourceHelper),
            color = colorTest,
            image = imageTest,
            themeName = themeName
        )
        val horizontalScroll = TestSettingsElement.HorizontalScroll(
            id = THEME_ID,
            title = StringResource.StringSettings.ThemeTitle.getString(resourceHelper),
            list = listTheme.getHorizontalScrollThemes()
                .sortedBy { !it.isSelected }
        )
        val choice = TestSettingsElement.Choice(
            id = AVAILABILITY_ID,
            title = StringResource.StringSettings.TestAvailabilityTitle.getString(resourceHelper),
            itemFirst = TestSettingsElement.Choice.Item(
                title = StringResource.StringSettings.AvailabilityValueAll.getString(resourceHelper),
                value = TestAvailability.Public.toString(),
                isSelected = testSettingsItem.availability == TestAvailability.Public
            ),
            itemSecond = TestSettingsElement.Choice.Item(
                title = StringResource.StringSettings.AvailabilityValueLink.getString(resourceHelper),
                value = TestAvailability.Private.toString(),
                isSelected = testSettingsItem.availability == TestAvailability.Private
            )
        )
        val choiceOrder = TestSettingsElement.Choice(
            id = ORDER_ID,
            title = StringResource.StringSettings.QuestionOrderTitle.getString(resourceHelper),
            itemFirst = TestSettingsElement.Choice.Item(
                title = StringResource.StringSettings.OrderValueOrder.getString(resourceHelper),
                value = TestQuestionOrder.Sequencial.toString(),
                isSelected = testSettingsItem.testQuestionOrder == TestQuestionOrder.Sequencial
            ),
            itemSecond = TestSettingsElement.Choice.Item(
                title = StringResource.StringSettings.OrderValueRandom.getString(resourceHelper),
                value = TestQuestionOrder.Shuffled.toString(),
                isSelected = testSettingsItem.testQuestionOrder == TestQuestionOrder.Shuffled
            )
        )
        val textInputMinAnswer = TestSettingsElement.TestInput(
            id = TITLE_MIN_CURRENT_ANSWER_ID,
            title = StringResource.StringSettings.MinCorrectAnswerTitle.getString(resourceHelper),
            valueInput = testSettingsItem.minCorrectAnswer.toString(),
            StringResource.StringSettings.MinCorrectAnswerHint.getString(resourceHelper)
        )
        val selectableShow = TestSettingsElement.Selectable(
            id = PREVIEW_SELECTABLE_ID,
            title =StringResource.StringSettings.PreShowQuestionTitle.getString(resourceHelper),
            description = "",
            isSelected = testSettingsItem.allowPreviewQuestions ?: false
        )
        val selectableAntiCheat = TestSettingsElement.Selectable(
            id = ANTI_CHEAT_SELECTABLE_ID,
            title = StringResource.StringSettings.AntiCheatTitle.getString(resourceHelper),
            description = StringResource.StringSettings.AntiCheatDescription.getString(resourceHelper),
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