package com.testeducation.screen.tests.creation

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.logic.screen.tests.creation.TestCreationSideEffect
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.screen.tests.creation.TestCreationModelState.StepState.Companion.isFirst
import com.testeducation.utils.getColor
import com.testeducation.utils.getString
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect


class TestCreationViewModel(
    reducer: IReducer<TestCreationModelState, TestCreationState>,
    private val getThemes: GetThemes,
    private val resourceHelper: IResourceHelper,
    errorHandler: IExceptionHandler
) : BaseViewModel<TestCreationModelState, TestCreationState, TestCreationSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: TestCreationModelState = TestCreationModelState()

    init {
        updateLoadingState(TestCreationModelState.LoadingState.LOADING)
        loadingTheme()
        initItemIconDesign()
        updateBtnText(getBtnText(TestCreationModelState.StepState.FIRST))
    }

    fun changeStateStep() = intent {
        val currentStepState = getModelState().stepState
        if (currentStepState.isFirst()) {
            TestCreationModelState.StepState.SECOND
        } else {
            TestCreationModelState.StepState.FIRST
        }.also { stepStateNew ->
            updateStateStep(stepStateNew)
            updateBtnText(getBtnText(stepStateNew))
        }
    }

    fun onTextChanged(text: String) = intent {
        updateModelState {
            copy(title = text)
        }
    }

    fun updateThemeSelected(id: String) = intent {
        getModelState().themes.find {
            it.id == id
        }?.let { themeSelected ->
            updateModelState {
                copy(
                    selectedTheme = themeSelected
                )
            }
        }
    }

    fun changeColor(colorState: TestCreationModelState.ColorState) = intent {
        launchJob {
            val colorFromResource = getCurrentColorFromResource(colorState)
            val updatedIconDesignItem =
                getModelState().iconDesign.changeColorIconDesignItem(colorFromResource)
            updateModelState {
                copy(
                    colorState = colorState,
                    iconDesign = updatedIconDesignItem
                )
            }
        }
    }

    fun changeStyleTestCard(style: CardTestStyle) = intent {
        launchJob {
            val newListStyleItems = getModelState().iconDesign.map { item ->
                item.copy(isSelected = style == item.style)
            }
            updateModelState {
                copy(
                    styleCurrent = style,
                    iconDesign = newListStyleItems
                )
            }
        }
    }

    private fun getCurrentColorFromResource(colorState: TestCreationModelState.ColorState) =
        when (colorState) {
            TestCreationModelState.ColorState.GREEN -> ColorResource.Main.Green
            TestCreationModelState.ColorState.BLUE -> ColorResource.Main.Blue
            TestCreationModelState.ColorState.RED -> ColorResource.Main.Red
            TestCreationModelState.ColorState.ORANGE -> ColorResource.Main.Orange
        }.run {
            getColor(
                resourceHelper
            )
        }

    private fun List<IconDesignItem>.changeColorIconDesignItem(backgroundColor: Int) =
        this.map { item ->
            item.copy(
                backgroundColor = backgroundColor
            )
        }

    private fun initItemIconDesign() {
        val items = mutableListOf<IconDesignItem>()
        val colorDefaultGreen = ColorResource.Main.Green.getColor(resourceHelper)
        for (style in CardTestStyle.values()) {
            val image = resourceHelper.getDrawableStyleTestCard(style)
            IconDesignItem(
                style = style,
                image = image,
                backgroundColor = colorDefaultGreen,
                isSelected = style == CardTestStyle.X
            ).also { itemCompleted ->
                items.add(itemCompleted)
            }
        }
        updateItemIconDesign(items)
    }

    private fun updateItemIconDesign(list: List<IconDesignItem>) = intent {
        updateModelState {
            copy(
                iconDesign = list
            )
        }
    }

    private fun updateStateStep(stateStepState: TestCreationModelState.StepState) = intent {
        updateModelState {
            copy(
                stepState = stateStepState
            )
        }
    }

    private fun loadingTheme() = intent {
        getThemes().collect { themes ->
            updateModelState {
                copy(
                    themes = themes,
                    loadingState = TestCreationModelState.LoadingState.IDLE
                )
            }
            postSideEffect(
                TestCreationSideEffect.CreateChip(
                    themes = themes.toNotSelectedUiList()
                )
            )
        }

    }

    private fun List<ThemeShort>.toNotSelectedUiList() = this.map { theme ->
        ThemeShortUI(
            theme.id,
            theme.title,
            isSelected = false
        )
    }

    private fun updateLoadingState(loadingState: TestCreationModelState.LoadingState) = intent {
        updateModelState {
            copy(
                loadingState = loadingState
            )
        }
    }

    private fun getBtnText(step: TestCreationModelState.StepState) = if (step.isFirst()) {
        StringResource.Common.CommonCancel
    } else {
        StringResource.Common.CommonBack
    }.run {
        getString(resourceHelper)
    }

    private fun updateBtnText(text: String) = intent {
        launchJob {
            updateModelState {
                copy(
                    backBtnText = text
                )
            }
        }
    }
}