package com.testeducation.ui.screen.tests.filters

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.chip.ChipDrawable
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.screen.tests.filters.TestsFiltersViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestsFiltersBinding
import com.testeducation.ui.utils.addThemes
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class TestsFiltersFragment : ViewModelHostFragment<TestsFiltersViewModel, FragmentTestsFiltersBinding>(
    TestsFiltersViewModel::class,
    FragmentTestsFiltersBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeData()
        setupViews()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestsFiltersState) = binding {
        renderThemes(state.themes)
    }

    private fun FragmentTestsFiltersBinding.renderThemes(themes: List<ThemeShortUI>) {
        themeChips.addThemes(themes, viewModel::selectTheme)
    }

    private fun setupViews() = binding {
//        val chipDrawable = ChipDrawable.createFromAttributes(
//            requireContext(),
//            null,
//            0,
//            R.style.ChipStyle
//        )
//        unlimited.setChipDrawable(chipDrawable)
//        limited.setChipDrawable(chipDrawable)
    }

    private fun setupListeners() = binding {
        btnClose.setClickListener(viewModel::exit)
        etFromQuestionsCount.addTextChangedListener {

        }
        etToQuestionsCount.addTextChangedListener {

        }
        etToTime.addTextChangedListener {

        }
        etFromTime.addTextChangedListener {

        }

    }

}
