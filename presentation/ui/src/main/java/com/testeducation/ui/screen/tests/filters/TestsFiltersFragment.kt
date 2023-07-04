package com.testeducation.ui.screen.tests.filters

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.view.isEmpty
import androidx.core.view.isGone
import androidx.core.view.isNotEmpty
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
import com.testeducation.ui.utils.switchHalfVisibleState
import com.testeducation.ui.utils.trimmedTextOrEmpty
import com.testeducation.utils.firstByConditionOrNull

class TestsFiltersFragment : ViewModelHostFragment<TestsFiltersViewModel, FragmentTestsFiltersBinding>(
    TestsFiltersViewModel::class,
    FragmentTestsFiltersBinding::inflate
) {

    private var questionsFromTextWatcher: TextWatcher? = null
    private var questionsToTextWatcher: TextWatcher? = null
    private var timeFromTextWatcher: TextWatcher? = null
    private var timeToTextWatcher: TextWatcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeData()
        setupViews()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun render(state: TestsFiltersState) = binding {
        renderThemes(state.themes, state.selectedThemeIndex)
        etPlaceHolderFromTime.isGone = !state.isTimeLimited
        etToTimePlaceHolder.isGone = !state.isTimeLimited
        limited.isChecked = state.isTimeLimited
        unlimited.isChecked = !state.isTimeLimited
        loadingProgress.isGone = !state.isLoading
        btnShowResults.text = if (state.isLoading) "" else resources.getQuantityString(
            R.plurals.tests_count_plurals,
            state.filterResultCount,
            state.filterResultCount
        )
        btnShowResults.switchHalfVisibleState(!state.isLoading)
    }

    private fun FragmentTestsFiltersBinding.renderThemes(
        themes: List<ThemeShortUI>,
        selectedThemeIndex: Int?
    ) {
        if (themeChips.isEmpty()) {
            themeChips.addThemes(themes, viewModel::selectTheme)
        }
        selectedThemeIndex?.let(themeChips::check) ?: themeChips.clearCheck()
    }

    private fun onSideEffect(sideEffect: TestsFiltersSideEffect) = when(
        sideEffect
    ) {
        is TestsFiltersSideEffect.SetTextFilters ->{
            removeTextListeners()
            binding.renderFilters(sideEffect)
            setupTextListeners()
        }
    }

    private fun FragmentTestsFiltersBinding.renderFilters(
        sideEffect: TestsFiltersSideEffect.SetTextFilters
    ) {
        etFromQuestionsCount.setText(sideEffect.minQuestionCount)
        etToQuestionsCount.setText(sideEffect.maxQuestionsCount)
        etToTime.setText(sideEffect.maxTimeLimit)
        etFromTime.setText(sideEffect.minTimeLimit)
    }

    private fun removeTextListeners() = with(binding) {
        etFromQuestionsCount.removeTextChangedListener(questionsFromTextWatcher)
        etToQuestionsCount.removeTextChangedListener(questionsToTextWatcher)
        etFromTime.removeTextChangedListener(timeFromTextWatcher)
        etToTime.removeTextChangedListener(timeToTextWatcher)
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
        questionsFromTextWatcher = etFromQuestionsCount.addTextChangedListener {
            viewModel.onMinQuestionsCountChanged(etFromQuestionsCount.trimmedTextOrEmpty)
        }
        questionsToTextWatcher = etToQuestionsCount.addTextChangedListener {
            viewModel.onMaxQuestionsCountChanged(etToQuestionsCount.trimmedTextOrEmpty)
        }
        timeToTextWatcher = etToTime.addTextChangedListener {
            viewModel.onMaxAnswerTimeChanged(etToTime.trimmedTextOrEmpty)
        }
        timeFromTextWatcher = etFromTime.addTextChangedListener {
            viewModel.onMinAnswerTimeChanged(etFromTime.trimmedTextOrEmpty)
        }
    }

    private fun setupListeners() = binding {
        btnClose.setClickListener(viewModel::exit)
        btnShowResults.setClickListener(viewModel::showResults)
        limited.setClickListener(viewModel::setLimited)
        unlimited.setClickListener(viewModel::setUnlimited)
        tvRefresh.setClickListener(viewModel::resetFilters)
        setupTextListeners()
    }

}
