package com.testeducation.screen.tests.creation

import androidx.lifecycle.viewModelScope
import com.testeducation.converter.test.toNotSelectedUiList
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.logic.screen.auth.login.LoginSideEffect
import com.testeducation.logic.screen.tests.creation.TestCreationSideEffect
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.screen.tests.creation.TestCreationModelState.StepState.Companion.isFirst
import com.testeducation.utils.getColor
import com.testeducation.utils.getString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect


class TestCreationViewModel(
    reducer: IReducer<TestCreationModelState, TestCreationState>,
    private val getThemes: GetThemes,
    private val resourceHelper: IResourceHelper,
    errorHandler: IExceptionHandler
) :
    BaseViewModel<TestCreationModelState, TestCreationState, TestCreationSideEffect>(
        reducer,
        errorHandler
    ) {
    override val initialModelState: TestCreationModelState = TestCreationModelState()

    private val modelState
        get() = run { viewModelScope.async { getModelState() } }

    init {
        updateLoadingState(TestCreationModelState.LoadingState.LOADING)
        loadingTheme()
        initItemIconDesign()
    }

    fun changeStateStep() {
        viewModelScope.launch {
            val currentStepState = getModelState().stepState
            if (currentStepState.isFirst()) {
                TestCreationModelState.StepState.SECOND
            } else {
                TestCreationModelState.StepState.FIRST
            }.also { stepStateNew ->
                updateStateStep(stepStateNew)
            }
        }
    }

    fun onTextChanged(text: String) = intent {
        updateModelState {
            copy(title = text)
        }
    }

    fun updateThemeSelected(title: String) = intent {
        modelState.await().themes.find {
            it.title == title
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
            updateModelState {
                copy(
                    colorState = colorState
                )
            }
        }
    }

    private fun initItemIconDesign() {
        val items = mutableListOf<IconDesignItem>()
        val colorDefaultGreen = ColorResource.Main.Green.getColor(resourceHelper)
        for (style in CardTestStyle.values()) {
            val image = resourceHelper.extractDrawableResource(style)
            IconDesignItem(
                style = style,
                image = image,
                backgroundColor = colorDefaultGreen,
                isSelected = false
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
        launchJob {
            val themes = getThemes()
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

    private fun updateLoadingState(loadingState: TestCreationModelState.LoadingState) = intent {
        updateModelState {
            copy(
                loadingState = loadingState
            )
        }
    }
}