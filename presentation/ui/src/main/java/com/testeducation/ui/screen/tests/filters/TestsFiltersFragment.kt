package com.testeducation.ui.screen.tests.filters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.google.android.material.chip.ChipDrawable
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.logic.screen.tests.filters.TestsFiltersSideEffect
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.screen.tests.filters.TestsFiltersViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestsFiltersBinding
import com.testeducation.ui.utils.addThemes
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.trimmedTextOrEmpty
import com.testeducation.utils.firstByConditionOrNull

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

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun render(state: TestsFiltersState) = binding {
        renderThemes(state.themes, state.selectedThemeIndex)
    }

    private fun FragmentTestsFiltersBinding.renderThemes(
        themes: List<ThemeShortUI>,
        selectedThemeIndex: Int?
    ) {
        themeChips.removeAllViews()
        themeChips.addThemes(themes, viewModel::selectTheme)
        selectedThemeIndex?.let(themeChips::check)
    }

    private fun onSideEffect(sideEffect: TestsFiltersSideEffect) = when(
        sideEffect
    ) {
        is TestsFiltersSideEffect.SetTextFilters -> binding.renderFilters(sideEffect)
    }

    private fun FragmentTestsFiltersBinding.renderFilters(
        sideEffect: TestsFiltersSideEffect.SetTextFilters
    ) {
        etFromQuestionsCount.setText(sideEffect.minQuestionCount)
        etToQuestionsCount.setText(sideEffect.maxQuestionsCount)
        etToTime.setText(sideEffect.maxTimeLimit)
        etFromTime.setText(sideEffect.minTimeLimit)
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

    private fun setupTextListeners() = binding {
        etFromQuestionsCount.addTextChangedListener {
            viewModel.onMinQuestionsCountChanged(etFromQuestionsCount.trimmedTextOrEmpty)
        }

        etToQuestionsCount.addTextChangedListener {
            viewModel.onMaxQuestionsCountChanged(etToQuestionsCount.trimmedTextOrEmpty)
        }

        etToTime.addTextChangedListener {
            viewModel.onMinAnswerTimeChanged(etToTime.trimmedTextOrEmpty)
        }

        etFromTime.addTextChangedListener {
            viewModel.onMaxAnswerTimeChanged(etFromTime.trimmedTextOrEmpty)
        }
    }

    private fun setupListeners() = binding {
        btnClose.setClickListener(viewModel::exit)
        setupTextListeners()
    }

}
