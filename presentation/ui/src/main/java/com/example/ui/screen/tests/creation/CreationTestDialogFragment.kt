package com.example.ui.screen.tests.creation

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.example.logic.model.theme.ThemeShortUI
import com.example.logic.screen.tests.creation.TestCreationState
import com.example.screen.tests.creation.TestCreationViewModel
import com.example.ui.R
import com.example.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.example.ui.databinding.DialogCreationTestBinding
import com.example.ui.utils.dp
import com.example.ui.utils.observe
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.shape.ShapeAppearanceModel

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
        binding.root.layoutParams.height = Resources.getSystem().displayMetrics.heightPixels
    }

    private fun render(testCreationState: TestCreationState) {
        val x = testCreationState.themes.toMutableList()
        x.addAll(
            listOf(
                ThemeShortUI("1", "ЙЦЦфвы"),
                ThemeShortUI("2", "ЙЦЦфвыfdsfds"),
                ThemeShortUI("3", "ЙЦЦфвыczxcz"),
                ThemeShortUI("4", "ЙЦЦфвы czxesc"),
                ThemeShortUI("5", "ЙЦЦфвыds"),
                ThemeShortUI("6", "ЙЦЦфвыsss")
            )
        )
        x.forEach {
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