package com.testeducation.ui.screen.tests.creation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.logic.screen.tests.creation.TestCreationSideEffect
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.screen.tests.creation.TestCreationModelState
import com.testeducation.screen.tests.creation.TestCreationViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogCreationTestBinding
import com.testeducation.ui.delegates.tests.testCreationBackgroundIconDelegates
import com.testeducation.ui.utils.addThemes
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil

class CreationTestDialogFragment :
    ViewModelHostBottomSheetDialog<DialogCreationTestBinding, TestCreationViewModel>(
        TestCreationViewModel::class,
        DialogCreationTestBinding::inflate
    ) {

    override val isFullScreen: Boolean
        get() = true

    private val iconDesignAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(IconDesignItem::style),
            testCreationBackgroundIconDelegates(viewModel::changeStyleTestCard)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecycler()
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun setupRecycler() = binding {
        rvIcon.apply {
            adapter = iconDesignAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            itemAnimator = null
        }
    }

    private fun setupListeners() = binding {
        inputText.addTextChangedListener(viewModel::onTextChanged)
        btnNext.setClickListener(viewModel::next)
        btnCancel.setClickListener(viewModel::back)
        firstColor.setClickListener {
            viewModel.changeColor(colorState = TestCreationModelState.ColorState.GREEN)
        }
        secondColor.setClickListener {
            viewModel.changeColor(colorState = TestCreationModelState.ColorState.BLUE)
        }
        threeColor.setClickListener {
            viewModel.changeColor(colorState = TestCreationModelState.ColorState.RED)
        }
        fourColor.setClickListener {
            viewModel.changeColor(colorState = TestCreationModelState.ColorState.ORANGE)
        }
    }

    private fun render(testCreationState: TestCreationState) = binding {
        changeStepVisible(isFirstVisible = testCreationState.isFirstScreenVisible)
        iconDesignAdapter.items = testCreationState.iconDesignList
        cardTest.setContent(testCreationState.testShortUI)
        btnCancel.text = testCreationState.btnCancelText
        btnNext.text = testCreationState.btnNextText
        changeVisibleProgressBar(testCreationState.visibleProgressBar)
    }

    private fun onSideEffect(sideEffect: TestCreationSideEffect) {
        when (sideEffect) {
            is TestCreationSideEffect.CreateChip -> {
                generateChips(sideEffect.themes)
            }
            is TestCreationSideEffect.TitleInputError -> {
                binding.inputText.setErrorMsg(sideEffect.error)
            }
        }
    }

    private fun generateChips(themes: List<ThemeShortUI>) = binding {
        chGroupTheme.removeAllViews()
        chGroupTheme.addThemes(
            themes = themes,
            isSelectedFirst = true,
            onChipSelected = viewModel::updateThemeSelected
        )
    }

    private fun changeStepVisible(isFirstVisible: Boolean) = binding {
        containerFirst.isVisible = isFirstVisible
        containerSecond.isVisible = !isFirstVisible
    }

    private fun changeVisibleProgressBar(isVisibleProgressBar: Boolean) = binding {
        loadingProgress.isVisible = isVisibleProgressBar
        if (isVisibleProgressBar) {
            containerFirst.isVisible = false
            containerSecond.isVisible = false
        }
        btnNext.isVisible = !isVisibleProgressBar
        btnCancel.isVisible = !isVisibleProgressBar
    }

}