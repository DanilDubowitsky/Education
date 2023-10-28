package com.testeducation.screen.tests.edit.style

import androidx.lifecycle.viewModelScope
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.UpdateTestStyle
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.logic.screen.tests.settings.TestSettingsSideEffect
import com.testeducation.logic.screen.tests.settings.TestStyleChangerState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getColor
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent

class TestStyleChangerViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestStyleChangerModelState, TestStyleChangerState>,
    private val resourceHelper: IResourceHelper,
    private val updateTestStyle: UpdateTestStyle,
    private val testId: String,
    private val color: String,
    private val themeName: String,
    private val backgroundImage: String,
    private val router: NavigationRouter,
) : BaseViewModel<TestStyleChangerModelState, TestStyleChangerState, TestSettingsSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestStyleChangerModelState = TestStyleChangerModelState()

    init {
        initItemIconDesign()
    }

    fun exit() {
        router.exit()
    }

    fun update() {
        viewModelScope.launch {
            val modelState = getModelState()
            updateTestStyle(
                testId, modelState.colorState.color, modelState.styleCurrent.name
            )
            router.sendResult(
                NavigationScreen.Tests.TestStyleChangerData.OnTestStyleChanger,
                NavigationScreen.Tests.TestStyleChangerData.OnTestStyleChanger.TestStyleChangerResult(
                    color = modelState.colorState.color,
                    styleCurrent = CardTestStyle.valueOf(modelState.styleCurrent.name)
                )
            )
            router.exit()
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

    fun changeColor(colorState: TestStyleChangerModelState.ColorState) = intent {
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

    private fun List<IconDesignItem>.changeColorIconDesignItem(backgroundColor: Int) =
        this.map { item ->
            item.copy(
                backgroundColor = backgroundColor
            )
        }

    private fun getCurrentColorFromResource(colorState: TestStyleChangerModelState.ColorState) =
        when (colorState) {
            TestStyleChangerModelState.ColorState.GREEN -> ColorResource.Main.Green
            TestStyleChangerModelState.ColorState.BLUE -> ColorResource.Main.Blue
            TestStyleChangerModelState.ColorState.RED -> ColorResource.Main.Red
            TestStyleChangerModelState.ColorState.ORANGE -> ColorResource.Main.Orange
        }.run {
            getColor(
                resourceHelper
            )
        }

    private fun updateItemIconDesign(list: List<IconDesignItem>) = intent {
        updateModelState {
            copy(
                iconDesign = list
            )
        }
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
        intent {
            updateModelState {
                copy(
                    colorState = TestStyleChangerModelState.ColorState.getColor(color),
                    styleCurrent = CardTestStyle.valueOf(backgroundImage),
                    selectedTheme = ThemeShort(title = themeName, id = "")
                )
            }
        }
    }
}