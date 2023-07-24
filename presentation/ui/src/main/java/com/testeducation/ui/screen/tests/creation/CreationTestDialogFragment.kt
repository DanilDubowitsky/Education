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
        viewModel.observe(this, ::render, ::onSideEffect)
        binding.rvIcon.apply {
            adapter = iconDesignAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            itemAnimator = null
        }
        binding.inputText.addTextChangedListener(viewModel::onTextChanged)
        onClickListenerProcess()
    }

    private fun render(testCreationState: TestCreationState) {
        changeStepVisible(isFirstVisible = testCreationState.isFirstScreenVisible)
        iconDesignAdapter.items = testCreationState.iconDesignList
        binding.cardTest.setContent(testCreationState.testShortUI)
        binding.btnCancel.text = testCreationState.btnCancelText
    }

    private fun onSideEffect(sideEffect: TestCreationSideEffect) {
        when (sideEffect) {
            is TestCreationSideEffect.CreateChip -> {
                generateChips(sideEffect.themes)
            }
        }
    }

    private fun generateChips(themes: List<ThemeShortUI>) = binding {
        chGroupTheme.addThemes(themes, viewModel::updateThemeSelected)
    }

    private fun onClickListenerProcess() = binding {
        btnNext.setOnClickListener {
            viewModel.changeStateStep()
        }
        btnCancel.setOnClickListener {
            viewModel.changeStateStep()
        }
        firstColor.setOnClickListener {
            viewModel.changeColor(colorState = TestCreationModelState.ColorState.GREEN)
        }
        secondColor.setOnClickListener {
            viewModel.changeColor(colorState = TestCreationModelState.ColorState.BLUE)
        }
        threeColor.setOnClickListener {
            viewModel.changeColor(colorState = TestCreationModelState.ColorState.RED)
        }
        fourColor.setOnClickListener {
            viewModel.changeColor(colorState = TestCreationModelState.ColorState.ORANGE)
        }
    }

    private fun changeStepVisible(isFirstVisible: Boolean) = binding {
        containerFirst.isVisible = isFirstVisible
        containerSecond.isVisible = !isFirstVisible
    }

}