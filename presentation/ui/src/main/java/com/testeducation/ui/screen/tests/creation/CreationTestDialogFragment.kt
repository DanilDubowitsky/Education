package com.testeducation.ui.screen.tests.creation


import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.screen.tests.creation.TestCreationViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogCreationTestBinding
import com.testeducation.ui.utils.dp
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class CreationTestDialogFragment :
    ViewModelHostBottomSheetDialog<DialogCreationTestBinding, TestCreationViewModel>(
        TestCreationViewModel::class,
        DialogCreationTestBinding::inflate
    ) {

    override val isFullScreen: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observe(this, ::render)
        onClickListenerProcess()
    }

    private fun render(testCreationState: TestCreationState) {
        testCreationState.themes.forEach {
            val chipDrawableS =
                ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.ChipStyle)
            Chip(
                requireContext(),
            ).apply {
                id = ViewCompat.generateViewId()
                setChipDrawable(chipDrawableS)
                shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(8.dp.toFloat())
                text = it.title
            }.run {
                binding.chGroupTheme.addView(this)
            }
        }
        changeStepVisible(isFirstVisible = testCreationState.isFirstScreenVisible)
    }

    private fun onClickListenerProcess() {
        binding {
            btnNext.setOnClickListener {
                viewModel.changeStateStep()
            }
        }
    }

    private fun changeStepVisible(isFirstVisible: Boolean) {
        binding {
            containerFirst.isVisible = isFirstVisible
            containerSecond.isVisible = !isFirstVisible
        }
    }

}