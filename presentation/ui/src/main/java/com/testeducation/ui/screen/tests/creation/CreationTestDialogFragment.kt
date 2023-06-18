package com.testeducation.ui.screen.tests.creation


import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.screen.tests.creation.TestCreationViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogCreationTestBinding
import com.testeducation.ui.utils.dp
import com.testeducation.ui.utils.observe

class CreationTestDialogFragment :
    ViewModelHostBottomSheetDialog<DialogCreationTestBinding, TestCreationViewModel>(
        TestCreationViewModel::class,
        DialogCreationTestBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observe(this, ::render)
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.root.layoutParams.height =
            Resources.getSystem().displayMetrics.heightPixels
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.window?.setDimAmount(0f)
        return dialog
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
    }

}